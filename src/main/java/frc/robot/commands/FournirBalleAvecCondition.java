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

//TODO : Créer deux versions de la commande :
/*FournirBalleSimple : necessite juste le convoyeur, n'utilise pas de conditions dans le execute
et va être utiliser dans SéquenceLancer

FournirBalleAvecCondition : utilise les deux conditions dans le execute, nécessite les 3 sous systèmes, va
être utiliser dans séquenceViserLancer
*/

public class FournirBalleAvecCondition extends CommandBase {
  //fournir les balles au lanceur à l'aide du convoyeur
  Convoyeur convoyeur;
  Lanceur lanceur;
  Tourelle tourelle;
  /**
   * Creates a new Convoyer.
   */
  public FournirBalleAvecCondition(Convoyeur convoyeur, Tourelle tourelle,Lanceur lanceur) {
    this.convoyeur= convoyeur;
    this.lanceur= lanceur;
    this.tourelle= tourelle;


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
    convoyeur.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
