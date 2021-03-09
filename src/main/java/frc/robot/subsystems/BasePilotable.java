/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.xml.sax.InputSource;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BasePilotable extends SubsystemBase {
  private CANSparkMax neog1 = new CANSparkMax(31, MotorType.kBrushless);
  private CANSparkMax neog2 = new CANSparkMax(30, MotorType.kBrushless);
  private CANSparkMax neod1 = new CANSparkMax(32, MotorType.kBrushless);
  private CANSparkMax neod2 = new CANSparkMax(33, MotorType.kBrushless);

  private SpeedControllerGroup neog = new SpeedControllerGroup(neog1, neog2);
  private SpeedControllerGroup neod = new SpeedControllerGroup(neod1, neod2);

  private DifferentialDrive drive = new DifferentialDrive(neog, neod);

  private Encoder encodeurg = new Encoder(0, 1,false);
  private Encoder encodeurd = new Encoder(2, 3,true);
  private double conversionEncodeur;

  private PigeonIMU gyro = new PigeonIMU(3);
  private double[] ypr = new double[3];
  //private double[] ypr_dps = new double[3];

  private DifferentialDriveOdometry odometrie;

  private boolean changementRampe = false;

  
  
    //Creates a new ExampleSubsystem.
   

  public BasePilotable() {
    resetEncodeur();
    resetGyro();
    conversionEncodeur=Math.PI*0.1865/(256*3*2.5); //roue de 18.46 cm déterminé manuellement, ratio 2.5:1 shaft-roue 3:1 encodeur-shaft encodeur 256 clic encodeur 
    
    setRamp(0);
    encodeurg.setDistancePerPulse(conversionEncodeur);
    encodeurd.setDistancePerPulse(conversionEncodeur);
    setNeutralMode(IdleMode.kCoast);
    odometrie= new DifferentialDriveOdometry(Rotation2d.fromDegrees(getAngle()));
    neog.setInverted(true);
    neod.setInverted(true);
  }

  @Override
  public void periodic() {
    //TODO Clean up du dashboard : j'oterais NeoEncodeur, Position Gauche et droite, Vitesse gauche et droite
    odometrie.update(Rotation2d.fromDegrees(getAngle()), getPositionG(), getPositionD());
    SmartDashboard.putNumberArray("odometrie", getOdometry());
    //SmartDashboard.putNumber("VitesseG", getVitesseG());
    //SmartDashboard.putNumber("VitesseD", getVitesseD());
    SmartDashboard.putNumber("Vitesse Moyenne", getVitesse());
    SmartDashboard.putNumber("Position Gauche", getPositionG());
    SmartDashboard.putNumber("Position Droite", getPositionD());
    SmartDashboard.putNumber("Position Moyenne", getPositionMoyenne());
    SmartDashboard.putNumber("NeoEncoder", getNeoEncoder());
    SmartDashboard.putNumber("Gyro", getAngle());
    SmartDashboard.putNumber("vitesse",getVitesse());

    //Changement de du ramp sur les NEO entre le mode autonome et téléop
    if (!changementRampe && RobotState.isOperatorControl()){
      setRamp(1);
      changementRampe = true;
    }

  }

  public void conduire(double vx, double vz) {
    drive.arcadeDrive(-vx, vz);
  }

  public void setRamp(double ramp) {
    neog1.setOpenLoopRampRate(ramp);
    neog2.setOpenLoopRampRate(ramp);
    neod1.setOpenLoopRampRate(ramp);
    neod2.setOpenLoopRampRate(ramp);
  }
  
  public void setNeutralMode(IdleMode mode){
    neog1.setIdleMode(mode);
    neog2.setIdleMode(mode);
    neod1.setIdleMode(mode);
    neod2.setIdleMode(mode);
  }
  
  public double getNeoEncoder(){//Pour test
    return -neog1.getEncoder().getPosition();
  }
  public double getPositionD() {
    return encodeurd.getDistance();
  }

  public double getPositionG() {
    return encodeurg.getDistance();
  }

  public double getPositionMoyenne(){
    return (getPositionG()+getPositionD())/2.0;
  }

  public void resetEncodeur() {
    encodeurd.reset();
    encodeurg.reset();
    neog1.getEncoder().setPosition(0);
  }
  public double getVitesseD() {
    return encodeurd.getRate();
  }

  public double getVitesseG() {
    return encodeurg.getRate();
  }
  public double getVitesse() {
    return (getVitesseD() + getVitesseG()) / 2;
  }
  //////après ce commentaire = Ramsete
 public double getAngle() {
    gyro.getYawPitchRoll(ypr);
    return ypr[0];
  } 
  
  public void resetGyro() {
    gyro.setYaw(0);
    
  }
  
  public double[] getOdometry(){
    double[] position = new double[3];
    double x = getPose().getTranslation().getX();
    double y = getPose().getTranslation().getY();
    double theta = getPose().getRotation().getDegrees();
    position[0] = x;
    position[1] = y;
    position[2] = theta;
    return position;

  }

 
  public Pose2d getPose() {
    return odometrie.getPoseMeters();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getVitesseG(), getVitesseD());
  }

  public void resetOdometrie(Pose2d pose) {
    resetEncodeur();
    resetGyro();
    odometrie.resetPosition(pose, Rotation2d.fromDegrees(getAngle()));
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    neog.setVoltage(leftVolts);
    neod.setVoltage(-rightVolts);
    drive.feed();
  }
 

 //À garder pour GalacticSearch
/*
  public double getTurnRate() {
    gyro.getRawGyro(ypr_dps);

    return ypr_dps[0]*-1;
  }*/
}
