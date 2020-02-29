/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Grimpeur;

public class BarrerGrimpeur extends CommandBase {
  Grimpeur grimpeur;  

  public BarrerGrimpeur(Grimpeur grimpeur) {
    this.grimpeur=grimpeur;
  }

  @Override
  public void initialize() {
    if(grimpeur.getPosition()>2){
      grimpeur.barrer();
    }
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
