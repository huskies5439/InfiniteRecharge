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

public class Tourelle extends SubsystemBase {
  NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
  NetworkTable limelight = networkTableInstance.getTable("limelight-huskies");
  NetworkTableEntry tv = limelight.getEntry("tv");
  NetworkTableEntry tx = limelight.getEntry("tx");
  WPI_TalonSRX tourelle = new WPI_TalonSRX(1);
  NetworkTableEntry ty = limelight.getEntry("ty"); 
  /** 
   * Creates a new Tourelle.
   */
  public Tourelle() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
