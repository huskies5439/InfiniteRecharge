// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Convoyeur;

public class FournirBalleSimple extends CommandBase {

  Convoyeur convoyeur;
  public FournirBalleSimple(Convoyeur convoyeur) {
    this.convoyeur=convoyeur;
    
    addRequirements(convoyeur);
  }

  @Override
  public void initialize() {
    convoyeur.fournirBalle();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    convoyeur.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
