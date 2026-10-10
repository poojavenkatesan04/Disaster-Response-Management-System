package com.disaster.gui;
import com.disaster.model.*;
import com.disaster.service.DisasterManagementService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
public class PeoplePanel extends JPanel {
    final DisasterManagementService s;
    final Runnable r;
    public PeoplePanel(DisasterManagementService x,Runnable y) {
        s=x;
        r=y;
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(18, 20, 18, 20));
        setBackground(UITheme.BG);
        JTabbedPane t=new JTabbedPane();
        t.addTab("Volunteers",tab(false));
        t.addTab("Emergency Teams",tab(true));
        add(t);
    }
    JPanel tab(boolean team) {
        JPanel p=new JPanel(new BorderLayout());
        DefaultTableModel m=new DefaultTableModel();
        JTable t=new JTable(m);
        JButton b=new JButton(team?"Add Team":"Add Volunteer");
        UITheme.styleButton(b, UITheme.BLUE);
        b.addActionListener(e->add(team));
        p.add(b,BorderLayout.NORTH);
        UITheme.styleTable(t);
        p.add(new JScrollPane(t),BorderLayout.CENTER);
        m.setColumnIdentifiers(team ? new Object[] {
            "ID","Name","Phone","Specialty","Available"
        }
        : new Object[] {
            "ID","Name","Phone","Skill","Available"
        }
        );
        if(team)for(EmergencyTeam z:s.getTeams())m.addRow(new Object[] {
            z.getId(),z.getDisplayName(),z.getContact(),z.getSpecialty(),z.isAvailable()
        }
        );
        else for(Volunteer z:s.getVolunteers())m.addRow(new Object[] {
            z.getId(),z.getDisplayName(),z.getContact(),z.getSkill(),z.isAvailable()
        }
        );
        return p;
    }
    void add(boolean team) {
        JTextField i=new JTextField(),n=new JTextField(),ph=new JTextField(),sk=new JTextField();
        JPanel p=new JPanel(new GridLayout(0,2));
        p.add(new JLabel("ID"));
        p.add(i);
        p.add(new JLabel("Name"));
        p.add(n);
        p.add(new JLabel("Phone"));
        p.add(ph);
        p.add(new JLabel(team?"Specialty":"Skill"));
        p.add(sk);
        if(JOptionPane.showConfirmDialog(this,p,team?"Add Team":"Add Volunteer",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)try {
            if(team)s.addTeam(new EmergencyTeam(i.getText(),n.getText(),ph.getText(),sk.getText()));
            else s.addVolunteer(new Volunteer(i.getText(),n.getText(),ph.getText(),sk.getText()));
            r.run();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage(),"Error",0);
        }
    }
}
