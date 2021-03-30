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

public class LancerAvecCible extends CommandBase {
 
  
  Lanceur lanceur;
  Limelight limelight;
  double ta;
  double ty;
  double vCible;

  public LancerAvecCible( Lanceur lanceur, Limelight limelight ) {
    this.lanceur= lanceur;
    this.limelight=limelight;
    addRequirements(lanceur);
  }
 
  @Override
  public void initialize() {
    limelight.camDetection();
    limelight.ledOn();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

   if (limelight.getTv()) {
      ta =limelight.getTa();
      vCible = lanceur.vShuffleBoard();//a calibrer
      lanceur.pidfController(vCible);
     
  }
  else {
    lanceur.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lanceur.stop();
    //limelight.camHumain();
    //limelight.ledOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
