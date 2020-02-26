/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Transmission;

public class ChangementVitesse extends CommandBase {
  Transmission transmission;
  BasePilotable basePilotable;
  //Initialise la State machine
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

  private State state = State.AUTO;
  /**
   * Creates a new ChangementVitesse.
   */
  //Initialise la commande ChangementVitesse
  public ChangementVitesse(BasePilotable basePilotable, Transmission transmission) {
    this.transmission=transmission;
    this.basePilotable=basePilotable;
    addRequirements(transmission);
  }

  @Override
  public void initialize() {
  }

  @Override
  //La logique des changements de vitesse
  public void execute() {
    if (RobotState.isAutonomous()){
       state= State.AUTO; 
      } 
    else if (Math.abs(basePilotable.getVitesse()) > 2 && state == State.LOW) { 
      transmission.hauteVitesse(); state =State.HIGH; 
  } 
    else if (Math.abs(basePilotable.getVitesse()) < 1.4 && state == State.HIGH) {
      transmission.basseVitesse(); state = State.LOW; 
      } 
    else if (state == State.AUTO) {
      transmission.basseVitesse(); 
    if (!RobotState.isAutonomous()) { 
      state = State.LOW; 
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
