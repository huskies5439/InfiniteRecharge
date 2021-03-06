/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.util.List;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.robot.commands.ChangementVitesse;
import frc.robot.commands.FournirBalle;
import frc.robot.commands.Gober;
import frc.robot.commands.Lancer;
import frc.robot.commands.RamseteSimple;
import frc.robot.commands.SequenceViserLancer;
import frc.robot.commands.TourelleAuto;
import frc.robot.commands.TourelleManuelle;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Convoyeur;
import frc.robot.subsystems.Gobeur;
import frc.robot.subsystems.Lanceur;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Tourelle;
import frc.robot.subsystems.Transmission;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
   private final BasePilotable basePilotable = new BasePilotable();
   private final Lanceur lanceur = new Lanceur();
   private final Gobeur gobeur = new Gobeur();
   private final Transmission transmission = new Transmission();
   private final Limelight limelight = new Limelight();
   private final Tourelle tourelle = new Tourelle();
  private final Convoyeur convoyeur = new Convoyeur();
  private final Compressor compressor = new Compressor(); 
  Trajectory exampleTrajectory = null;
  
  
  XboxController pilote = new XboxController(0);
  //XboxController copilote = new XboxController(1);

 
  public RobotContainer() {
    configureButtonBindings();
    basePilotable.setDefaultCommand(new RunCommand(()-> basePilotable.conduire(1.0*pilote.getY(GenericHID.Hand.kLeft), 0.7*pilote.getX(GenericHID.Hand.kRight)),basePilotable));
    tourelle.setDefaultCommand(new TourelleManuelle(()->(pilote.getTriggerAxis(Hand.kRight)-pilote.getTriggerAxis(Hand.kLeft))*-0.25, tourelle));//moins parce que maths
    transmission.setDefaultCommand(new ChangementVitesse(basePilotable, transmission));
    convoyeur.setDefaultCommand(new RunCommand(convoyeur::indexer, convoyeur));
  
  }                               

   private void configureButtonBindings(){

   new JoystickButton(pilote, Button.kBumperRight.value).whileHeld(new Gober(gobeur));

   new JoystickButton(pilote, Button.kY.value).toggleWhenPressed(new Lancer(lanceur,limelight));
   new JoystickButton(pilote, Button.kBumperLeft.value).whileHeld(new RunCommand(()->lanceur.setVitesse(11), lanceur).andThen(new InstantCommand(lanceur::stop)));
  
  // new JoystickButton(pilote, Button.kA.value).whenPressed(new SequenceViserLancer(tourelle, lanceur, limelight, convoyeur));
   //new JoystickButton(pilote, Button.kA.value).whileHeld(new TourelleAuto(tourelle,limelight));
   //new JoystickButton(pilote, Button.kB.value).whenPressed(new InstantCommand(transmission::basseVitesse,transmission));
   //new JoystickButton(pilote, Button.kX.value).whenPressed(new InstantCommand(transmission::hauteVitesse,transmission));
   new JoystickButton(pilote, Button.kB.value).whileHeld(new FournirBalle(convoyeur, tourelle, lanceur));

  
  }
 
  public Command getAutonomousCommand() {
     var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(Constants.kS, Constants.kV, 0),
        Constants.kinematics, 10); // 0.25, 1.95, 0.312

    /*TrajectoryConfig config = new TrajectoryConfig(Constants.maxVitesse, Constants.maxAcceleration)// max speed, max acceleration
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(Constants.kinematics)
        // Apply the voltage constraint
        .addConstraint(autoVoltageConstraint);*/

        String trajectoryJSON = "output/BarrelRacing.wpilib.json";
        try
        {
         var path = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
         DriverStation.reportWarning("Path : " + path,false);
         exampleTrajectory = TrajectoryUtil.fromPathweaverJson(path);
         /*var transform = basePilotable.getPose().minus(exampleTrajectory.getInitialPose());
         exampleTrajectory= exampleTrajectory.transformBy(transform);*/
         
        } 
        catch (IOException e)
        {
           DriverStation.reportError("Unable to open trajectory : " + trajectoryJSON, e.getStackTrace());
  
        
        }


        /*exampleTrajectory = TrajectoryGenerator.generateTrajectory(
          // Start at the origin facing the +X direction
          new Pose2d(0, 0, new Rotation2d(0)),
          // Pass through these two interior waypoints, making an 's' curve path
          List.of(
              new Translation2d(2, 1),
              new Translation2d(4, -1)
          ),
          // End 3 meters straight ahead of where we started, facing forward
          new Pose2d(6, 0, new Rotation2d(0)),
          // Pass config
          config
      );*/
      basePilotable.resetOdometrie(exampleTrajectory.getInitialPose());
      return new RamseteSimple(exampleTrajectory, basePilotable);
     // return new RunCommand(()->basePilotable.tankDriveVolts(4.38,4.38), basePilotable);
   }
}

