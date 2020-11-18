/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Convoyeur;
import frc.robot.subsystems.Lanceur;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Tourelle;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SequenceViserLancer extends ParallelCommandGroup {
  

  /**
   * Creates a new SequenceViserLancer.
   */
  public SequenceViserLancer(Tourelle tourelle, Lanceur lanceur,Limelight limelight,Convoyeur convoyeur) {
    addCommands(
      new TourelleAuto(tourelle, limelight),
      new Lancer(lanceur, limelight),
      new FournirBalle(convoyeur, tourelle, lanceur)
    );


    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
  }
}