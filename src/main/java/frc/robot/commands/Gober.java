/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//ok
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Gobeur;

public class Gober extends CommandBase {
  Gobeur gobeur;
  
  public Gober(Gobeur gobeur) {
    this.gobeur= gobeur;
    addRequirements(gobeur);
  }

  @Override
  public void initialize() {
    gobeur.tiroirOut();
    gobeur.moteurGobe();
  }

  @Override
  public void execute() {
    
  }

  @Override
  public void end(boolean interrupted) {
    gobeur.moteurStop();
    gobeur.tiroirIn();
  }

  @Override
  
    public boolean isFinished() {
      return false;
  }
}
