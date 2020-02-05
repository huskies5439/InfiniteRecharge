/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Gobeur;

public class Gober extends CommandBase {
  Gobeur gobeur;
  /**
   * Creates a new gobe.
   */
  public Gober(Gobeur gobeur) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.gobeur= gobeur;
    addRequirements(gobeur);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    gobeur.tiroirOut();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    gobeur.moteurGobe();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    gobeur.moteurStop();
    gobeur.tiroirIn();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
