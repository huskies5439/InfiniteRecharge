/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Transmission;
//ok
public class ChangementVitesse extends CommandBase {
  Transmission transmission;
  BasePilotable basePilotable;
  //Déclare et permet l'envoie des states dans le SmartDashboard.
  private enum State {
    LOW(0), HIGH(1), AUTO(2);

    private int stateValue;

    State(int stateValue) {
      this.stateValue = stateValue;
    }

    public int getState() {
      return stateValue;
    }

  }

  private State state = State.AUTO;//Initialise la state en Auto.

  public ChangementVitesse(BasePilotable basePilotable, Transmission transmission) {
    this.transmission=transmission;
    this.basePilotable=basePilotable;
    addRequirements(transmission);
  }

  @Override
  public void initialize() {
  }

  @Override
  //La logique des changements de vitesse.
  //Fait un suivi de la State a laquelle le Robot est.
  public void execute() {
    if (RobotState.isAutonomous()){
       state= State.AUTO; //Pas nécéssaire en compé, mais sert a retourner en Autonomous.
      } 
    else if (Math.abs(basePilotable.getVitesse()) > 2 && state == State.LOW) { 
      transmission.hauteVitesse(); 
      state =State.HIGH; //Vérifier que le Robot est en basseVitesse pour passer en hauteVitesse.
      } 
    else if (Math.abs(basePilotable.getVitesse()) < 1.4 && state == State.HIGH) {
      transmission.basseVitesse(); 
      state = State.LOW; //Vérifier que le Robot est en hauteVitesse pour passer en basseVitesse.
      } 
    else if (state == State.AUTO) {
      transmission.basseVitesse(); //Force la basseVitesse en Autonomous.
      if (!RobotState.isAutonomous()) { 
        state = State.LOW; //Après Autonomous passe dans la StateMachine principal.
        } 
      }
  
  //SmartDashboard.putNumber("State", state.getState());
     }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
