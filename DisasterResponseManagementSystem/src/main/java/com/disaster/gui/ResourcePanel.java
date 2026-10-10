package com.disaster.gui;
import com.disaster.model.*;
import com.disaster.service.DisasterManagementService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
public class ResourcePanel extends JPanel {
    final DisasterManagementService s;
    final Runnable r;
    public ResourcePanel(DisasterManagementService x,Runnable y) {
        s=x;
        r=y;
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(18, 20, 18, 20));
        setBackground(UITheme.BG);
        JTabbedPane t=new JTabbedPane();
        for(String k:new String[] {
            "Vehicle","Food","Medicine","Shelter"
        }
        )t.addTab(k,tab(k));
        add(t);
    }
    JPanel tab(String k) {
        JPanel p=new JPanel(new BorderLayout());
        DefaultTableModel m=new DefaultTableModel();
        JTable t=new JTable(m);
        JButton b=new JButton("Add "+k);
        UITheme.styleButton(b, UITheme.BLUE);
        b.addActionListener(e->add(k));
        p.add(b,BorderLayout.NORTH);
        UITheme.styleTable(t);
        p.add(new JScrollPane(t),BorderLayout.CENTER);
        if(k.equals("Vehicle")) {
            m.setColumnIdentifiers(new Object[] {
                "ID","Name","Type","Available"
            }
            );
            for(Vehicle z:s.getVehicles())m.addRow(new Object[] {
                z.getId(),z.getName(),z.getVehicleType(),z.isAvailable()
            }
            );
        } else if(k.equals("Food")) {
            m.setColumnIdentifiers(new Object[] {
                "ID","Name","Quantity","Available"
            }
            );
            for(Food z:s.getFoods())m.addRow(new Object[] {
                z.getId(),z.getName(),z.getQuantity(),z.isAvailable()
            }
            );
        } else if(k.equals("Medicine")) {
            m.setColumnIdentifiers(new Object[] {
                "ID","Name","Quantity","Available"
            }
            );
            for(Medicine z:s.getMedicines())m.addRow(new Object[] {
                z.getId(),z.getName(),z.getQuantity(),z.isAvailable()
            }
            );
        } else {
            m.setColumnIdentifiers(new Object[] {
                "ID","Name","Capacity","Occupied","Free"
            }
            );
            for(Shelter z:s.getShelters())m.addRow(new Object[] {
                z.getId(),z.getName(),z.getCapacity(),z.getOccupied(),z.getAvailableSpaces()
            }
            );
        }
        return p;
    }
    void add(String k) {
        JTextField i=new JTextField(),n=new JTextField(),v=new JTextField();
        JPanel p=new JPanel(new GridLayout(0,2));
        p.add(new JLabel("ID"));
        p.add(i);
        p.add(new JLabel("Name"));
        p.add(n);
        p.add(new JLabel(k.equals("Vehicle")?"Vehicle type":k.equals("Shelter")?"Capacity":"Quantity"));
        p.add(v);
        if(JOptionPane.showConfirmDialog(this,p,"Add "+k,JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)try {
            if(k.equals("Vehicle"))s.addVehicle(new Vehicle(i.getText(),n.getText(),v.getText()));
            else if(k.equals("Food"))s.addFood(new Food(i.getText(),n.getText(),Integer.parseInt(v.getText())));
            else if(k.equals("Medicine"))s.addMedicine(new Medicine(i.getText(),n.getText(),Integer.parseInt(v.getText())));
            else s.addShelter(new Shelter(i.getText(),n.getText(),Integer.parseInt(v.getText())));
            r.run();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage(),"Error",0);
        }
    }
}
