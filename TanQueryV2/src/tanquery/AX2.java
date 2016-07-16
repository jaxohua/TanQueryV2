package tanquery;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JPanel;
public class AX2 extends javax.swing.JFrame {
    /**
	 * 0
	 */
	private static final long serialVersionUID = 1L;


	AX2(){
    	setTitle("TanQuery MSICU");
        this.setSize(1024, 768); //Las Dimensiones de la Ventana
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        TanQuery sketch = new TanQuery();
        JPanel Datos = new JPanel();
        Datos.setBounds(0, 0, 1024, 768);
        getContentPane().add(Datos);
        
        sketch.setBounds(0, 0, 1024, 768);
        Datos.add(sketch);
        sketch.init();
        this.setVisible(true);
    	
        String query="select * from maestros_mx";
        try {
			sketch.conex.setSt(sketch.conex.con.createStatement());
			sketch.conex.setRs(sketch.conex.getSt().executeQuery(query));
			 ResultSet rs = sketch.conex.getRs();
			 Datos.setLayout(null);
			 JTable table;
			 table = new JTable(buildTableModel(rs));
			 
			 JScrollPane scrollPane = new JScrollPane(table);
			 scrollPane.setBounds(10, 350, 492, 308);
			 Datos.add(scrollPane);
			 
			 //JOptionPane.showMessageDialog(null, new JScrollPane(table));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.repaint();
        
    }
    
    public static void main(String[] args) {	
        JFrame gui=new AX2();
        gui.getContentPane();
        
    }
    

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
}

