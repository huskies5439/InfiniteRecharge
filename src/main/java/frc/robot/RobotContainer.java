/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.Gober;
import frc.robot.commands.Lancer;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Gobeur;
import frc.robot.subsystems.Lanceur;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
  
  
  
  XboxController pilote = new XboxController(0);
  //XboxController copilote = new XboxController(1);
  

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureButtonBindings();
    basePilotable.setDefaultCommand(new RunCommand(()-> basePilotable.conduire(1*pilote.getY(GenericHID.Hand.kLeft), 0.65*pilote.getX(GenericHID.Hand.kRight)),basePilotable));

    
  }                               


  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
   private void configureButtonBindings(){
   
   new JoystickButton(pilote, Button.kA.value).toggleWhenActive(new Gober(gobeur).andThen(new WaitCommand(2.0)).andThen(new InstantCommand(gobeur::moteurStop, gobeur)));
   
   new JoystickButton(pilote, Button.kY.value).toggleWhenActive(new Lancer(lanceur));
 
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  //public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return m_autoCommand;
  //}
}
