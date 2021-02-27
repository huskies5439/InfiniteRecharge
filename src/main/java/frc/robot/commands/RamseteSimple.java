/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.BasePilotable;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class RamseteSimple extends RamseteCommand {
  /**
   * Creates a new RamseteSimple.
   */
  
  public RamseteSimple(Trajectory trajectoire, BasePilotable basePilotable) {
    super(trajectoire, 
    basePilotable::getPose,
    new RamseteController(2, 0.7), 
    new SimpleMotorFeedforward(Constants.kS,Constants.kV, 0 ),
    Constants.kinematics,
    basePilotable::getWheelSpeeds, 
    new PIDController(Constants.kPRamsete, 0, 0), 
    new PIDController(Constants.kPRamsete, 0, 0),
    // RamseteCommand passes volts to the callback
    basePilotable::tankDriveVolts, basePilotable);
  }
}
