/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BasePilotable extends SubsystemBase {
  private CANSparkMax neog1 = new CANSparkMax(22, MotorType.kBrushless);
  private CANSparkMax neog2 = new CANSparkMax(23, MotorType.kBrushless);
  private CANSparkMax neod1 = new CANSparkMax(24, MotorType.kBrushless);
  private CANSparkMax neod2 = new CANSparkMax(25, MotorType.kBrushless);
  
  private SpeedControllerGroup neog = new SpeedControllerGroup(neog1, neog2);
  private SpeedControllerGroup neod = new SpeedControllerGroup(neod1, neod2);
  
  private DifferentialDrive drive = new DifferentialDrive(neog, neod);
  
  
  /**
   * Creates a new ExampleSubsystem.
   */

  public BasePilotable() {

  }

  @Override
  public void periodic() {
    
  }
  public void conduire(double vx, double vz){
    drive.curvatureDrive(-0.4*vx, -0.75*vz, false);
  }
}
