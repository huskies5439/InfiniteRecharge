/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Grimpeur;

public class ResetGrimpeur extends CommandBase {
    Grimpeur grimpeur;
    DoubleSupplier input;

  public ResetGrimpeur(DoubleSupplier input, Grimpeur grimpeur){
    this.grimpeur=grimpeur;
    this.input=input;
  }

 
  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    grimpeur.angleServo(input.getAsDouble()*160+10);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
