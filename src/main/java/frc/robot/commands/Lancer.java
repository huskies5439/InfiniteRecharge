/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lanceur;
import frc.robot.subsystems.Limelight;

public class Lancer extends CommandBase {
  Lanceur lanceur;
  Limelight limelight;
  double ta;
  double vCible;

  public Lancer( Lanceur lanceur, Limelight limelight ) {
    this.lanceur= lanceur;
    this.limelight=limelight;
    addRequirements(lanceur);
  }
 
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   ta =limelight.getTa();
   vCible = ta*100; //a calibrer

   lanceur.pidfController(vCible);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lanceur.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
