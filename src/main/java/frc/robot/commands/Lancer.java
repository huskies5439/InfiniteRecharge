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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lanceur;

public class Lancer extends CommandBase {
  Lanceur lanceur;
  private ShuffleboardTab tab = Shuffleboard.getTab("SmartDashboard");
  private NetworkTableEntry vlanceurmax =tab.add("vlanceurmax", 1).getEntry();
  /**
   * Creates a new Lancer.
   */
  public Lancer( Lanceur lanceur ) {
    this.lanceur= lanceur;
    addRequirements(lanceur);


    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    lanceur.vitesselancer(vlanceurmax.getDouble(0));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lanceur.vitesselancer(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
