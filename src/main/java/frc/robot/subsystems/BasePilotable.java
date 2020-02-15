/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;



import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
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

  private DoubleSolenoid vitesse = new DoubleSolenoid(0, 1);


  private enum State {
    LOW(0), HIGH(1), AUTO(2);
    private int stateValue;
    State(int stateValue)
   {
     this.stateValue = stateValue;
   }

   public int getState()
   {
     return stateValue;
   }
   
   
  }
  private State state = State.AUTO;

  
    //Creates a new ExampleSubsystem.
   

  public BasePilotable() {
    resetEncodeur();
    conversionEncodeur=(Math.PI*Units.inchesToMeters(7.24))/(256*3*2.5); //roue de 7.24134 pouces déterminé manuellement, ratio 2.5:1 shaft-roue 3:1 encodeur-shaft encodeur 256 clic encodeur 
    setRamp(0.15);
    encodeurg.setDistancePerPulse(conversionEncodeur);
    encodeurd.setDistancePerPulse(conversionEncodeur);
    basseVitesse();
    state= State.AUTO;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("PositionG", getPositionG());
    SmartDashboard.putNumber("PositionD", getPositionD());
    SmartDashboard.putNumber("VitesseG", getVitesseG());
    SmartDashboard.putNumber("VitesseD", getVitesseD());
    SmartDashboard.putNumber("Vitesse Moyenne", getVitesse());
    SmartDashboard.putNumber("State", state.getState());
/*
    if (RobotState.isAutonomous()){
      state= State.AUTO;
    }
    else if (Math.abs(getVitesse()) > 2 && state == State.LOW) {
      hauteVitesse();
      state = State.HIGH;
    } 
    else if (Math.abs(getVitesse()) < 1 && state == State.HIGH) {
      basseVitesse();
      state = State.LOW;
    }
    else if (state == State.AUTO) {
      basseVitesse();
      if (!RobotState.isAutonomous()) {
        state = State.LOW;
      }
    }*/
  }

  public void conduire(double vx, double vz) {
    drive.curvatureDrive(vx, -vz, Math.abs(vx)<0.2);
  }

  public void setRamp(double ramp) {
    neog1.setOpenLoopRampRate(ramp);
    neog2.setOpenLoopRampRate(ramp);
    neod1.setOpenLoopRampRate(ramp);
    neod2.setOpenLoopRampRate(ramp);
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

  public double getPositionD() {
    return encodeurd.getDistance();
  }

  public double getPositionG() {
    return encodeurg.getDistance();
  }

  public void resetEncodeur() {
    encodeurd.reset();
    encodeurg.reset();
  }


  public void hauteVitesse() {
    vitesse.set(Value.kForward);
  }

  public void basseVitesse() {
    vitesse.set(Value.kReverse);
  }
}
