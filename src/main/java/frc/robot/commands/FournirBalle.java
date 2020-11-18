/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Convoyeur;
import frc.robot.subsystems.Lanceur;
import frc.robot.subsystems.Tourelle;

public class FournirBalle extends CommandBase {
  //fournir les balles au lanceur à l'aide du convoyeur
  Convoyeur convoyeur;
  Lanceur lanceur;
  Tourelle tourelle;
  /**
   * Creates a new Convoyer.
   */
  public FournirBalle(Convoyeur convoyeur, Tourelle tourelle,Lanceur lanceur) {
    this.convoyeur= convoyeur;
    this.lanceur= lanceur;


    addRequirements(convoyeur);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(tourelle.estCentre()&& lanceur.estBonneVitesse()){
    convoyeur.fournirBalle();
      
    }
    
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
