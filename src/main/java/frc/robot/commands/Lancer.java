/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lanceur;

public class Lancer extends CommandBase {
  Lanceur lanceur;
 // private ShuffleboardTab tab = Shuffleboard.getTab("SmartDashboard");
  //private NetworkTableEntry vcible =tab.add("vitesse lanceur cible", 1).getEntry();
 
  
  public Lancer( Lanceur lanceur ) {
    this.lanceur= lanceur;
    addRequirements(lanceur); 
  }
 
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //7800, pageau sest plain que cetait pas max speed
    lanceur.pidfController(8000);
    
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
