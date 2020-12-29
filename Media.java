import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.text.Format;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.io.*;

class Time
{
    int seconds;
    int minutes;
    int hours;

    void error(JFrame frame)
    {
        JOptionPane.showMessageDialog(frame,"Please enter a valid time value","Error!", JOptionPane.ERROR_MESSAGE);
    }
}

class UI extends JFrame implements ActionListener
{
    Font style = new Font("TimesRoman", Font.PLAIN, 18);
    Font sub = new Font("TimesRoman", Font.PLAIN, 16);
    Border blackline = BorderFactory.createLineBorder(Color.black);
    File file;
    String saveFile="";

    String extension;
    Format time = DateFormat.getTimeInstance(DateFormat.SHORT);
    String[] tList = new String[]{"mp3","wav","mp4","mkv","MOV"};

    private static final long serialVersionUID = -4737755530764696856L;

    JFrame frame = new JFrame("Kronos");

    JLabel chooseIn = new JLabel();
    JLabel inputfield = new JLabel();
    JButton open = new JButton("Choose");

    JCheckBox trim = new JCheckBox("Trim");
    JLabel from = new JLabel("From");
    JFormattedTextField fromT;
    JLabel to = new JLabel("To");
    JFormattedTextField toT;
    JLabel duration = new JLabel("Duration");
    JLabel durationT = new JLabel();

    JLabel format = new JLabel("Format");
    JLabel source = new JLabel("Source Format");
    JLabel sourceText = new JLabel();
    JLabel target  = new JLabel("Target Format");
    JComboBox<String> targetText = new JComboBox<String>(tList);

    JLabel chooseOu = new JLabel();
    JLabel outputfield = new JLabel();
    JButton save = new JButton("Choose");

    JButton start = new JButton("Start Conversion");

    JFileChooser fileOpen = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    JFileChooser fileSave = new JFileChooser();

    UI()
    {
        frame.setSize(600,600);

        chooseIn.setBounds(10,10,300,20);
        chooseIn.setText("Choose the input file");
        chooseIn.setFont(style);

        inputfield.setBounds(10,40,450,30);
        inputfield.setOpaque(true);
        inputfield.setBackground(Color.WHITE);
        inputfield.setBorder(blackline);
        inputfield.setFont(sub);

        open.setBounds(470,40,100,30);
        open.setFont(sub);
        open.addActionListener(this);

        trim.setBounds(10,100,100,50);
        trim.addActionListener(this);

        try
        {
            MaskFormatter mask = new MaskFormatter("##:##:##");
            mask.setPlaceholderCharacter('0');
            fromT = new JFormattedTextField(mask);
            toT = new JFormattedTextField(mask);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }

        from.setBounds(10,160,50,30);
        from.setFont(sub);

        fromT.setBounds(70,160,100,30);
        fromT.setFont(sub);
        fromT.setHorizontalAlignment(JLabel.CENTER);
        fromT.setBorder(blackline);
        fromT.addActionListener(this);

        to.setBounds(190,160,50,30);
        to.setFont(sub);

        toT.setBounds(230,160,100,30);
        toT.setFont(sub);
        toT.setHorizontalAlignment(JLabel.CENTER);
        toT.setBorder(blackline);
        toT.addActionListener(this);

        from.setEnabled(false);
        fromT.setEnabled(false);
        to.setEnabled(false);
        toT.setEnabled(false);

        duration.setBounds(400,160,80,30);
        duration.setFont(sub);
        duration.setEnabled(false);

        durationT.setBounds(470,160,100,30);
        durationT.setFont(sub);
        durationT.setHorizontalAlignment(JLabel.CENTER);
        durationT.setOpaque(true);
        durationT.setBorder(blackline);
        durationT.setBackground(Color.WHITE);
        durationT.setText("00:00:00");
        durationT.setEnabled(false);

        format.setBounds(10,250,300,20);
        format.setFont(style);

        source.setBounds(40,300,120,20);
        source.setFont(sub);

        sourceText.setBounds(170,300,70,20);
        sourceText.setHorizontalAlignment(JLabel.CENTER);
        sourceText.setOpaque(true);
        sourceText.setBorder(blackline);
        sourceText.setBackground(Color.WHITE);

        target.setBounds(300,300,120,20);
        target.setFont(sub);

        targetText.setBounds(430,300,90,20);
        targetText.setBackground(Color.WHITE);
        targetText.addActionListener(this);

        chooseOu.setBounds(10,400,300,20);
        chooseOu.setText("Choose the save location");
        chooseOu.setFont(style);

        outputfield.setBounds(10,430,450,30);
        outputfield.setOpaque(true);
        outputfield.setBackground(Color.WHITE);
        outputfield.setBorder(blackline);
        outputfield.setFont(sub);

        save.setBounds(470,430,100,30);
        save.setFont(sub);
        save.addActionListener(this);

        start.setBounds(200,500,200,30);
        start.setFont(style);
        start.setHorizontalAlignment(JButton.CENTER);
        start.addActionListener(this);

        frame.add(chooseIn);
        frame.add(inputfield);
        frame.add(open);
        frame.add(trim);
        frame.add(from);
        frame.add(fromT);
        frame.add(to);
        frame.add(toT);
        frame.add(duration);
        frame.add(durationT);
        frame.add(format);
        frame.add(source);
        frame.add(sourceText);
        frame.add(target);
        frame.add(targetText);
        frame.add(chooseOu);
        frame.add(outputfield);
        frame.add(save);
        frame.add(start);

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e)
    {
        String filename;

        if(e.getSource()==open)
        {
            int r = fileOpen.showOpenDialog(frame);
            if (r==JFileChooser.APPROVE_OPTION)   
            {
                filename =  fileOpen.getSelectedFile().getAbsolutePath();
                this.file = new File(filename);
                fileSave.setCurrentDirectory(this.file.getParentFile());

                inputfield.setText(filename);
                int index = filename.lastIndexOf('.');
                extension = filename.substring(index+1);

                boolean flag=false;
                int i;

                for(i=0;i<tList.length;++i)
                    if(tList[i].compareTo(extension)==0)
                    {
                        flag=true;
                        break;
                    }

                if(flag==true)
                {
                    sourceText.setText(extension);
                    targetText.setSelectedIndex(i);
                }
                else    
                    sourceText.setText("Unsupported Format");
            }  
        }

        else if(e.getSource()==save)
        {
            int r = fileSave.showSaveDialog(frame);

            if(r==JFileChooser.APPROVE_OPTION)
            {
                saveFile = new String(fileSave.getSelectedFile().getAbsolutePath());
                outputfield.setText(saveFile+"."+targetText.getSelectedItem().toString());
            }
        }

        else if(e.getSource()==trim)
        {
            if(trim.isSelected())
            {
                from.setEnabled(true);
                fromT.setEnabled(true);
                to.setEnabled(true);
                toT.setEnabled(true);
                duration.setEnabled(true);
                durationT.setEnabled(true);
            }
            else
            {
                from.setEnabled(false);
                fromT.setEnabled(false);
                to.setEnabled(false);
                toT.setEnabled(false);
                duration.setEnabled(false);
                durationT.setEnabled(false);
            }
        }

        else if(e.getSource()==fromT || e.getSource()==toT)
        {            
            Time fromTime = new Time();
            Time toTime = new Time();
            Time diff = new Time();

            fromTime.hours = Integer.parseInt(fromT.getText().substring(0,2));
            fromTime.minutes = Integer.parseInt(fromT.getText().substring(3,5));
            fromTime.seconds = Integer.parseInt(fromT.getText().substring(6,8));

            toTime.hours = Integer.parseInt(toT.getText().substring(0,2));
            toTime.minutes = Integer.parseInt(toT.getText().substring(3,5));
            toTime.seconds = Integer.parseInt(toT.getText().substring(6,8));

            if(fromTime.minutes>59 || fromTime.seconds>59 || toTime.minutes>59 || toTime.seconds>59)
                diff.error(frame);

            else
            {
                if(fromTime.seconds>toTime.seconds)
                {
                    --toTime.minutes;
                    toTime.seconds+=60;
                }
    
                diff.seconds = toTime.seconds-fromTime.seconds;

                if(fromTime.minutes>toTime.minutes)
                {
                    --toTime.hours;
                    toTime.minutes+=60;
                }
                
                diff.minutes=toTime.minutes-fromTime.minutes;
                diff.hours=toTime.hours-fromTime.hours;

                if(diff.hours<0 || diff.minutes<0 || diff.seconds<0)
                    diff.error(frame);

                else
                {
                    DecimalFormat formatter = new DecimalFormat("00");
                    String hours = formatter.format((diff.hours));
                    String minutes = formatter.format((diff.minutes));
                    String seconds = formatter.format((diff.seconds));

                    String durationTime = hours+":"+minutes+":"+seconds;
                    this.durationT.setText(durationTime);
                }
            }
        }

        else if(e.getSource()==targetText)
        {
            String outputExtension  = targetText.getSelectedItem().toString();
            int index;
            String currentOutput,newOutput;

            if(saveFile=="")
            {
                currentOutput = inputfield.getText();
                index = currentOutput.lastIndexOf('.');
                newOutput = currentOutput.substring(0,index)+"_edit."+outputExtension;
            }
            
            else
            {
                currentOutput = outputfield.getText();                
                index = currentOutput.lastIndexOf('.');
                newOutput = currentOutput.substring(0,index)+"."+outputExtension;
            }

            this.outputfield.setText(newOutput);
        }

        else if(e.getSource()==start)
        {
            String ffmpegCommand;

            if(sourceText.getText()=="Unsupported Format" || sourceText.getText()==" ")
                JOptionPane.showMessageDialog(frame,"Please choose a valid file","Error!", JOptionPane.ERROR_MESSAGE);

            else
            {
                if(trim.isSelected())
                    ffmpegCommand = "ffmpeg -i "+inputfield.getText()+" -ss "+fromT.getText()+" -to "+toT.getText()+" "+outputfield.getText();
                else
                    ffmpegCommand = "ffmpeg -i "+inputfield.getText()+" "+outputfield.getText();

                System.out.println(ffmpegCommand);

                try
                {
                    Process process = Runtime.getRuntime().exec(ffmpegCommand);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                    String line="";
                    while ((line = reader.readLine ()) != null) 
                        System.out.println(line);

                    int exitCode = process.waitFor();

                    if(exitCode==0)
                    {
                        System.out.println("Process Complete");
                        JOptionPane.showMessageDialog(frame,"Conversion Complete");
                    }

                    else
                        System.out.println("\nExited with error code : " + exitCode);

                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(frame,"An Exception occurred","ERROR!", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }
}

public class Media
{
    public static void main(String[] args)
    {
        new UI();
    }
}