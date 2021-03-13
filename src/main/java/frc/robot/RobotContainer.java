/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//TODO : Clean-up de tous les imports inutiles

package frc.robot;

import java.io.IOException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.commands.ChangementVitesse;
import frc.robot.commands.Gober;
import frc.robot.commands.RamseteSimple;
import frc.robot.commands.SequenceLancer;
import frc.robot.commands.SequenceViserLancer;
import frc.robot.commands.TourelleManuelle;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Convoyeur;
import frc.robot.subsystems.Gobeur;
import frc.robot.subsystems.Lanceur;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Tourelle;
import frc.robot.subsystems.Transmission;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
   private final BasePilotable basePilotable = new BasePilotable();
   private final Lanceur lanceur = new Lanceur();
   private final Gobeur gobeur = new Gobeur();
   private final Transmission transmission = new Transmission();
   private final Limelight limelight = new Limelight();
   private final Tourelle tourelle = new Tourelle();
   private final Convoyeur convoyeur = new Convoyeur();
   Trajectory autonomousTrajectory = null;
   private SendableChooser<String> chooser = new SendableChooser<>();
  
  XboxController pilote = new XboxController(0);


  public RobotContainer() {
    configureButtonBindings();
   
    basePilotable.setDefaultCommand(new RunCommand(()-> basePilotable.conduire(pilote.getY(GenericHID.Hand.kLeft), pilote.getX(GenericHID.Hand.kRight)),basePilotable));
    tourelle.setDefaultCommand(new TourelleManuelle(()->(pilote.getTriggerAxis(Hand.kRight)-pilote.getTriggerAxis(Hand.kLeft))*-0.25, tourelle));//moins parce que maths
    transmission.setDefaultCommand(new ChangementVitesse(basePilotable, transmission));
    //convoyeur.setDefaultCommand(new RunCommand(convoyeur::indexer, convoyeur));
    
    chooser.setDefaultOption("Test", "Test");
    chooser.addOption("BarrelRacing", "BarrelRacing");
    chooser.addOption("Slalom", "Slalom");
    chooser.addOption("Bounce", "Bounce");
    chooser.addOption("Galactic Search", "GalacticSearch");
    SmartDashboard.putData("Auto Mode", chooser);

  }                               

   private void configureButtonBindings(){

   new JoystickButton(pilote, Button.kBumperRight.value).whileHeld(new Gober(gobeur));

   new JoystickButton(pilote, Button.kX.value).toggleWhenPressed(new SequenceLancer(lanceur, convoyeur));
  
   new JoystickButton(pilote, Button.kA.value).toggleWhenPressed(new SequenceViserLancer(tourelle, lanceur, limelight, convoyeur));
   
  }
 
  public Command getAutonomousCommand() {

   String trajet = chooser.getSelected();

        String trajectoryJSON = "output/"+trajet+".wpilib.json";
        try
        {
         var path = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
         DriverStation.reportWarning("Path : " + path,false);
         autonomousTrajectory = TrajectoryUtil.fromPathweaverJson(path);
         
        } 
        catch (IOException e)
        {
           DriverStation.reportError("Unable to open trajectory : " + trajectoryJSON, e.getStackTrace());
   
      
        }

      basePilotable.resetOdometrie(autonomousTrajectory.getInitialPose());
      if (trajet == "GalacticSearch"){
         return new ParallelCommandGroup(new Gober(gobeur), new RamseteSimple(autonomousTrajectory, basePilotable));
      }
      else { 
         return new RamseteSimple(autonomousTrajectory, basePilotable);
      }
     
     
   }
}

