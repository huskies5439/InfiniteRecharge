/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  private NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
  private NetworkTable limelight = networkTableInstance.getTable("limelight-huskies");
  private NetworkTableEntry tv = limelight.getEntry("tv");
  private NetworkTableEntry tx = limelight.getEntry("tx");
  private NetworkTableEntry ta = limelight.getEntry("ta");
  private NetworkTableEntry ty = limelight.getEntry("ty");
  private NetworkTableEntry ledMode = limelight.getEntry("ledMode");
  private NetworkTableEntry camMode = limelight.getEntry("camMode");

  /**
   * Creates a new Limelight.
   */
  public Limelight() {

  }

  public double getTa() {
    return ta.getDouble(0);
  }

  public boolean getTv() {
    return tv.getDouble(0)==1;
  }

  public double getTx() {
    return tx.getDouble(0);
  }

  public double getTy() {
    return ty.getDouble(0);
  }

  public void ledOn() {
    ledMode.setNumber(3);
  }
  public void ledOff() {
    ledMode.setNumber(1);
  }
  public void camHumain(){
    camMode.setNumber(1);
  }
  public void camDetection(){
    camMode.setNumber(0);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
