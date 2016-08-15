package tanquery;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
//import javax.swing.JButton;

@SuppressWarnings("serial")
public class MyTanQuery extends javax.swing.JFrame {
	TanQuery sketch;
	JTable table;
	JPanel panel;
	JPanel pDatos;
	JPanel pSketch;
	JScrollPane scrollPane;
	JLabel lblNewLabel;
	JLabel lblNewLabel2;
	DefaultTableModel model;
	Integer h=0; 
	int reset=0;
//linea

	public static void main(String[] args) throws SQLException {
		MyTanQuery mt=new MyTanQuery();
		mt.getDatos();
	}

	
	
	public MyTanQuery() throws SQLException {
		//setExtendedState(MAXIMIZED_BOTH);

		setTitle("TanQuery MSICU");
		// Las Dimensiones de la Ventana
		//setSize(1280, 700);
		//setSize(800, 600);
		setSize(1280, 1024);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		getContentPane().setLayout(null);

		/*JButton btnIniciar = new JButton("iniciar");
		btnIniciar.setBounds(0, 0, 117, 29);
		getContentPane().add(btnIniciar);
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getDatos();
			}
		});*/

		lblNewLabel2 = new JLabel("New label");
		lblNewLabel2.setBounds(0, 20, 240, 16);
		//getContentPane().add(lblNewLabel2);

		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(0, 40, 240, 16);
		//getContentPane().add(lblNewLabel);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 1280, 800);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		pDatos = new JPanel();
		pDatos.setBorder(null);
		pDatos.setBackground(Color.PINK);
		pDatos.setBounds(10, 260, 492, 300);
		panel.add(pDatos);
		pDatos.setLayout(null);
		Color ivory2=new Color(0,0,0);
		pDatos.setBackground(ivory2);
		
		table = new JTable();
		table.setBorder(null);
		table.setForeground(Color.WHITE);
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		table.setBounds(0, 0, 486, 302);
		table.setOpaque(true);
		table.setFillsViewportHeight(true);
		Color ivory=new Color(34,34,34);
		table.setBackground(ivory);

		// pDatos.add(table);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(-2, 0, 497, 305);
		pDatos.add(scrollPane);
		setVisible(true);

		pSketch = new JPanel();
		pSketch.setBounds(0, 0, 1280, 800);
		panel.add(pSketch);

		sketch = new TanQuery();
		sketch.frameRate = 60.0f;
		sketch.setBounds(0, 0, 1280, 800);
		sketch.init();
		pSketch.setLayout(null);
		pSketch.add(sketch);
		sketch.setLayout(null);
		
		setVisible(true);
		
	}

	private void getDatos() {
		System.out.println("Entrando a getDatos");
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected void done() {
				h++;
				System.out.println("El hilo ya termino: "+h+" veces.");
			}

			@Override
			protected Void doInBackground() {
				
				while (true) {
					if (sketch.actualizar==true && sketch.querySQL!="") {
						getResult(sketch, table);
						System.out.println("Entrando en el Hilo..."
								+ sketch.querySQL);
					} else {
						reset++;
						lblNewLabel.setText("Reseteando "+reset);
					}

					try {
						Thread.sleep(505);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		worker.execute();
	}

	public void getResult(TanQuery sketch, JTable table){
		try {
			ResultSet rs;
			sketch.conex.setSt(sketch.conex.con.createStatement());
			sketch.conex.setRs(sketch.conex.getSt().executeQuery(sketch.querySQL));
			rs = sketch.conex.getRs();
			model = buildTableModel(rs);
			table.setModel(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// JOptionPane.showMessageDialog(null, new JScrollPane(table));
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
