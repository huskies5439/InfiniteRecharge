/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Tourelle;

public class TourelleAuto extends CommandBase {
  Tourelle tourelle;
  Limelight limelight;
  double vitesse;

  /**
   * Creates a new TourelleAuto.
   */
  public TourelleAuto(Tourelle tourelle, Limelight limelight) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.tourelle = tourelle;
    this.limelight = limelight;
    addRequirements(tourelle);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    limelight.camDetection();
    limelight.ledOn();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (limelight.getTv()) {
      vitesse = tourelle.pidController(limelight.getTx());
      SmartDashboard.putString("Avertissement Cible", "YA UNE CIBLE !!!");
        if (tourelle.getSoftLimit(vitesse)) {
          tourelle.setVoltage(vitesse);
        }
        else {
          tourelle.stop();
        }
    }
    else {
      SmartDashboard.putString("Avertissement Cible", "PAS DE CIBLE !!!");
      tourelle.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    limelight.camHumain();
    limelight.ledOff();
    tourelle.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
