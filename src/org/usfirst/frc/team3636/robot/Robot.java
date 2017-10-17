package org.usfirst.frc.team3636.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3636.robot.commands.ExampleCommand;
import org.usfirst.frc.team3636.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    public static OI oi;
    
    RobotDrive myRobot = new RobotDrive(0, 1); // class that handles basic drive
    // operations
    
    public Joystick leftStick = new Joystick(0);
    public Joystick rightStick = new Joystick(1);
//    public Button leftButton = new JoystickButton(leftStick, 0);
//    public Button rightButton = new JoystickButton(rightStick, 1);
//    public Spark s = new Spark(2);
    Timer timer = new Timer();

//    public final double autoleft = -.25; 
//    public final double autoright = -.25;
    public final double timerdelay = .1;
    public final int motorspeed = 1; 
    public final int TIME_AUTO = 6; //Change to autonomous time in seconds
    public final double AUTO_SPEED = .3; //This controls the speed of autonomous
    public final double CURVE_CHANGE =-.01;
        
    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        oi = new OI();
        chooser.addDefault("Default Auto", new ExampleCommand());
        // chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     *
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();
        timer.reset();
        timer.start();
        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector",
         * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
         * = new MyAutoCommand(); break; case "Default Auto": default:
         * autonomousCommand = new ExampleCommand(); break; }
         */

        // schedule the autonomous command (example)
        if (autonomousCommand != null)
            autonomousCommand.start();
        
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        System.out.println("time: " + timer.get());
        while (timer.get() < TIME_AUTO){
        	myRobot.drive(AUTO_SPEED,CURVE_CHANGE);
        	//myRobot.tankDrive(AUTO_SPEED,AUTO_SPEED);
        }
//        teleopInit();
//        teleopPeriodic();
        /*if(timer.get()<15){
        	myRobot.tankDrive(autoleft,autoright);
        	
        }
        else{
            myRobot.tankDrive(0, 0);
        }*/
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
            autonomousCommand.cancel();
        myRobot.setSafetyEnabled(true);
        
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        double leftval = -leftStick.getY();
        double rightval= -rightStick.getY();
        myRobot.tankDrive(leftval, rightval);
        Timer.delay(0.005); // wait for a motor update time
        
//        /*if (leftStick.getRawButton(3)) {
//            //leftButton.whileHeld(new TestCommand());
//        }*/
//        if (leftStick.getTrigger()){
//            s.set(motorspeed);
//            Timer.delay(timerdelay);
//        }
//        if (rightStick.getTrigger()){
//            s.set(-motorspeed);
//            Timer.delay(timerdelay);
//        }
//        else{
//            s.set(0);
//        }
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }
}