/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;

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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.robot.commands.ChangementVitesse;
import frc.robot.commands.Gober;
import frc.robot.commands.Lancer;
import frc.robot.commands.TourelleAuto;
import frc.robot.commands.TourelleManuelle;
import frc.robot.subsystems.BasePilotable;
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

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final BasePilotable basePilotable = new BasePilotable();
  private final Lanceur lanceur = new Lanceur();
  private final Gobeur gobeur = new Gobeur();
  private final Transmission transmission = new Transmission();
  private final Limelight limelight = new Limelight();
  private final Tourelle tourelle = new Tourelle();
  Trajectory exampleTrajectory = null;
  
  
  XboxController pilote = new XboxController(0);
  //XboxController copilote = new XboxController(1);
  

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureButtonBindings();
    basePilotable.setDefaultCommand(new RunCommand(()-> basePilotable.conduire(1.0*pilote.getY(GenericHID.Hand.kLeft), 0.7*pilote.getX(GenericHID.Hand.kRight)),basePilotable));
    tourelle.setDefaultCommand(new TourelleManuelle(()->(pilote.getTriggerAxis(Hand.kRight)-pilote.getTriggerAxis(Hand.kLeft))*-0.25, tourelle));//moins parce que maths
    transmission.setDefaultCommand(new ChangementVitesse(basePilotable, transmission));
  }                               


  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
   private void configureButtonBindings(){
   
   new JoystickButton(pilote, Button.kBumperRight.value).whileHeld(new Gober(gobeur));
   
   new JoystickButton(pilote, Button.kY.value).toggleWhenPressed(new Lancer(lanceur));
  
   new JoystickButton(pilote, Button.kA.value).whileHeld(new TourelleAuto(tourelle,limelight));
   //new JoystickButton(pilote, Button.kB.value).whenPressed(new InstantCommand(transmission::basseVitesse,transmission));
   //new JoystickButton(pilote, Button.kX.value).whenPressed(new InstantCommand(transmission::hauteVitesse,transmission));
   }
  /*
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
     //An ExampleCommand will run in autonomous
     var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(0.25, 1.95, 0.312),
        Constants.kinematics, 5); // 0.25, 1.95, 0.312

    TrajectoryConfig config = new TrajectoryConfig(1, 0.5)// max speed, max acceleration
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(Constants.kinematics)
        // Apply the voltage constraint
        .addConstraint(autoVoltageConstraint);
        exampleTrajectory = TrajectoryGenerator.generateTrajectory(
          // Start at the origin facing the +X direction
          new Pose2d(0, 0, new Rotation2d(0)),
          // Pass through these two interior waypoints, making an 's' curve path
          List.of(
              new Translation2d(1, 1),
              new Translation2d(2, -1)
          ),
          // End 3 meters straight ahead of where we started, facing forward
          new Pose2d(3, 0, new Rotation2d(0)),
          // Pass config
          config
      );
      RamseteCommand ramseteCommand = new RamseteCommand(exampleTrajectory, basePilotable::getPose,
      new RamseteController(2, 0.7), 
      new SimpleMotorFeedforward(0.25, 1.95, 0 ),
      Constants.kinematics,
      basePilotable::getWheelSpeeds, 
      new PIDController(0, 0, 0), 
      new PIDController(0, 0, 0), // 12.2
      // RamseteCommand passes volts to the callback
      basePilotable::tankDriveVolts, basePilotable);// 8.92

      return ramseteCommand.andThen(() -> 
      basePilotable.tankDriveVolts(0, 0)).beforeStarting(()-> basePilotable.resetOdometrie(new Pose2d()));
      //return new RunCommand(()->basePilotable.tankDriveVolts(-1, 1));
   }
}

