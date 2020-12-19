import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;

class UI extends JFrame implements ActionListener
{
    private static final long serialVersionUID = -4737755530764696856L;
    JFrame frame = new JFrame("Kronos");
    JLabel chooseIn = new JLabel();
    JTextField inputfield = new JTextField();
    JButton choose = new JButton("Choose");
    Font style = new Font("TimesRoman", Font.PLAIN, 18);
    Font sub = new Font("TimesRoman", Font.PLAIN, 16);

    UI()
    {
        frame.setSize(600,600);
        chooseIn.setBounds(10,10,300,20);
        chooseIn.setText("Choose the input file");
        chooseIn.setFont(style);
        inputfield.setBounds(10,40,450,30);
        inputfield.setFont(sub);
        choose.setBounds(470,40,100,30);
        choose.addActionListener(this);

        frame.add(chooseIn);
        frame.add(inputfield);
        frame.add(choose);


        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==choose)
        {
            JFileChooser file = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = file.showOpenDialog(null);
            if (r==JFileChooser.APPROVE_OPTION)   
            { 
                inputfield.setText(file.getSelectedFile().getAbsolutePath()); 
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