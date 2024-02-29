package paquete1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AsistenteInversion extends JFrame {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JTextField comisionField;
	private JButton comenzarButton;
	private ArrayList<Activo> activos;
	private JButton[] botonesMenu;
	private int indiceBotonSeleccionado;
	private JLabel comisionLabel;
	public AsistenteInversion() {
		UIManager.put("OptionPane.okButtonText", "OK");
		UIManager.put("OptionPane.cancelButtonText", "Cancel");
		UIManager.put("OptionPane.yesButtonText", "Yes");
		UIManager.put("OptionPane.noButtonText", "No");
		setTitle("Investment Assistant");
		setSize(500,  400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setIconImage(getIconImage());
		comisionLabel = new JLabel("Brokerage commission (%):");
		comisionField = new JTextField(10);
		comenzarButton = new JButton("Start");
		comenzarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarAsistente();
			}
		});
		add(comisionLabel);
		add(comisionField);
		add(comenzarButton);
		activos = new ArrayList<>();
		comisionField.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            try {
		                double comision = Double.parseDouble(comisionField.getText());
		                if (comision >=  0) {
		                    iniciarAsistente();
		                }
		            } catch (NumberFormatException ex) {
		            }
		        }
		    }
		});

		mostrarPantallaCarga();
	}
	private void mostrarPantallaCarga() {
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		JLabel tituloLabel = new JLabel("Investment Assistant");
		tituloLabel.setHorizontalAlignment(JLabel.CENTER);
		tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
		JLabel subtituloLabel = new JLabel("Loading...");
		subtituloLabel.setHorizontalAlignment(JLabel.CENTER);
		subtituloLabel.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		getContentPane().add(tituloLabel, BorderLayout.NORTH);
		getContentPane().add(subtituloLabel, BorderLayout.CENTER);
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true); 
		getContentPane().add(progressBar, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null); 
		setVisible(true);
		setResizable(false);
		Timer timer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().removeAll();
				getContentPane().setLayout(new FlowLayout());
				add(comisionLabel);
				add(comisionField);
				add(comenzarButton);         
				activos = new ArrayList<>();

				comisionField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							iniciarAsistente();
						}
					}
				});
				comisionField.requestFocusInWindow();  
				pack();
				setLocationRelativeTo(null); 
				setVisible(true);    
			}
		});
		timer.setRepeats(false); 
		timer.start(); 
	}
	@Override
	public Image getIconImage () {
		Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagenes/icon.png.png"));
		return retValue;
	}
	private void iniciarAsistente() {
	    try {
	        double comision = Double.parseDouble(comisionField.getText());
	        if (comision <  0) {
	            JOptionPane.showMessageDialog(this, "The brokerage commission must be equal to or greater than  0");
	        } else {
	            mostrarMenuPrincipal(comision);
	        }
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "The brokerage commission must be a numerical value.");
	    }
	}
	private void mostrarMenuPrincipal(double comision) {
		getContentPane().removeAll();
		JButton botonIntroducir = new JButton("NEW ASSET");
		botonIntroducir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarIntroducirActivo(comision);
			}
		});
		JButton botonSimular = new JButton("SIMULATE PRICE FLUCTUATIONS");
		botonSimular.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimularFluctiacion();
			}
		});
		JButton botonEliminar = new JButton("DELETE ASSET");
		botonEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EliminarActivo();
			}
		});
		JButton botonMostrar = new JButton("MY ASSETS");
		botonMostrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MostrarMostrarActivos();
			}
		});
		JButton botonAbout = new JButton("ABOUT");
		botonAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarInfoContacto();
			}
		});
		JButton botonSalir = new JButton("EXIT");
		botonSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int opcion = JOptionPane.showConfirmDialog(null, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION);
				if (opcion == JOptionPane.YES_OPTION) {
					System.exit(0); 
				}
			}
		});
		JPanel panelBotones = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx =   1; 
		gbc.gridx =   0;
		gbc.gridy =   0;
		panelBotones.add(botonIntroducir, gbc);
		gbc.gridy =   1;
		panelBotones.add(botonSimular, gbc);
		gbc.gridy =   2;
		panelBotones.add(botonEliminar, gbc);
		gbc.gridy =   3;
		panelBotones.add(botonMostrar, gbc);
		gbc.gridy =   4;
		panelBotones.add(botonAbout, gbc);
		gbc.gridy =   5;
		panelBotones.add(botonSalir, gbc);   
		getContentPane().add(panelBotones, BorderLayout.CENTER);
		botonesMenu = new JButton[]{botonIntroducir, botonSimular, botonEliminar, botonMostrar, botonAbout, botonSalir};  
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); 
		getContentPane().add(Box.createVerticalGlue());
		getContentPane().add(botonIntroducir);
		getContentPane().add(Box.createVerticalStrut(10)); 
		getContentPane().add(botonSimular);
		getContentPane().add(Box.createVerticalStrut(10));
		getContentPane().add(botonEliminar);
		getContentPane().add(Box.createVerticalStrut(10)); 
		getContentPane().add(botonMostrar);
		getContentPane().add(Box.createVerticalStrut(10)); 
		getContentPane().add(botonAbout);
		getContentPane().add(Box.createVerticalStrut(10)); 
		getContentPane().add(botonSalir);
		getContentPane().add(Box.createVerticalGlue()); 
		for (Component button : getContentPane().getComponents()) {
			if (button instanceof JButton) {
				((JButton) button).setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
			}
		}
		botonesMenu = new JButton[]{botonIntroducir, botonSimular, botonEliminar, botonMostrar, botonAbout, botonSalir};
		for (int i =  0; i < botonesMenu.length; i++) {
			botonesMenu[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					int codigoTecla = e.getKeyCode();
					if (codigoTecla == KeyEvent.VK_UP) {
						if (indiceBotonSeleccionado >  0) {
							indiceBotonSeleccionado--;
						}
					} else if (codigoTecla == KeyEvent.VK_DOWN) {
						if (indiceBotonSeleccionado < botonesMenu.length -  1) {
							indiceBotonSeleccionado++;
						}
					} else if (codigoTecla == KeyEvent.VK_ENTER) {
						botonesMenu[indiceBotonSeleccionado].doClick();
					}
					botonesMenu[indiceBotonSeleccionado].requestFocusInWindow();
				}
			});
		}
		indiceBotonSeleccionado =  0;
		botonesMenu[indiceBotonSeleccionado].requestFocusInWindow();
		setSize(480,  260);
		setResizable(false);
		setLocationRelativeTo(null);

		revalidate();
		repaint();
	}
	private void mostrarInfoContacto() {
		String email = "franciscojavierayalaparejo@gmail.com";
		String linkedin = "linkedin.com/in/francisco-javier-ayala-parejo";
		String github = "github.com/franciscojavierayala";
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.PLAIN,  12));
		StringBuilder sb = new StringBuilder();
		sb.append("Email: ").append(email).append("\n");
		sb.append("LinkedIn: ").append(linkedin).append("\n");
		sb.append("GitHub: ").append(github).append("\n");
		textArea.setText(sb.toString());
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500,  400));
		JOptionPane.showMessageDialog(this, scrollPane, "About", JOptionPane.INFORMATION_MESSAGE);
	}
	private void mostrarIntroducirActivo(double comision) {
		JDialog ventanaIntroducirCompra = new JDialog(this, "New Asset", true);
		ventanaIntroducirCompra.setSize(300, 200);
		ventanaIntroducirCompra.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		JLabel precioLabel = new JLabel("Asset Value:");
		JTextField precioField = new JTextField(10);
		JLabel inversionLabel = new JLabel("Amount invested:");
		JTextField inversionField = new JTextField(10);
		JLabel fechaLabel = new JLabel("Date (dd-mm-yyyy):");
		JTextField fechaField = new JTextField(10);
		JButton botonGuardar = new JButton("Save");
		KeyListener enterListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Object source = e.getSource();
					if (source == precioField) {
						inversionField.requestFocusInWindow();
					} else if (source == inversionField) {
						fechaField.requestFocusInWindow();
					} else if (source == fechaField) {
						botonGuardar.requestFocusInWindow();
					}
				}
			}
		};
		precioField.addKeyListener(enterListener);
		inversionField.addKeyListener(enterListener);
		fechaField.addKeyListener(enterListener);
		gbc.gridx = 0;
		gbc.gridy = 0;
		ventanaIntroducirCompra.add(precioLabel, gbc);
		gbc.gridx = 1;
		ventanaIntroducirCompra.add(precioField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		ventanaIntroducirCompra.add(inversionLabel, gbc);
		gbc.gridx = 1;
		ventanaIntroducirCompra.add(inversionField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		ventanaIntroducirCompra.add(fechaLabel, gbc);
		gbc.gridx = 1;
		ventanaIntroducirCompra.add(fechaField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		ventanaIntroducirCompra.add(botonGuardar, gbc);
		ActionListener guardarListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarCompra(precioField, inversionField, fechaField, ventanaIntroducirCompra, comision);
			}
		};
		botonGuardar.addActionListener(guardarListener);
		botonGuardar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					botonGuardar.doClick();
				}
			}
		});
		ventanaIntroducirCompra.setResizable(false);
		centrarVentana(ventanaIntroducirCompra);
		ventanaIntroducirCompra.setVisible(true);
	}
	private void guardarCompra(JTextField precioField, JTextField inversionField, JTextField fechaField, JDialog ventanaIntroducirCompra, double comision) {
		try {
			double precio = obtenerNumeroValido(precioField.getText(), "Asset Value");
			double inversion = obtenerNumeroValido(inversionField.getText(), "Amount invested");
			String fecha = obtenerFechaValida(fechaField.getText());
			activos.add(new Activo(precio, inversion, comision, fecha));
			JOptionPane.showMessageDialog(ventanaIntroducirCompra, "Asset saved successfully.");
			ventanaIntroducirCompra.dispose();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(ventanaIntroducirCompra, "Please enter valid numeric values.");
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(ventanaIntroducirCompra, ex.getMessage());
		}
	}
	private void SimularFluctiacion() {
	    if (activos.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "There are no assets to simulate fluctuation.");
	        return;
	    }
	    double precioSupuesto = 0;
	    while (true) {
	        String input = JOptionPane.showInputDialog(this, "Enter the value to simulate:");
	        if (input == null) {
	            return;
	        }
	        try {
	            precioSupuesto = obtenerNumeroValido(input, "Simulated Value");
	            if (precioSupuesto >= 0) {
	                break;
	            } else {
	                JOptionPane.showMessageDialog(this, "Please enter a non-negative numerical value.");
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Please enter a valid numerical value.");
	        } catch (IllegalArgumentException e) {
	            JOptionPane.showMessageDialog(this, e.getMessage());
	        }
	    }
	    StringBuilder resultado = new StringBuilder();
	    resultado.append("Simulated Value: ").append(precioSupuesto).append("\n\n");
	    double capitalTotal = 0;
	    double rendimientoTotal = 0;
	    double totalComisiones = 0;
	    for (Activo activo : activos) {
	        double precioCompra = activo.getPrecio();
	        double inversionNeta = activo.getInversionNeta();
	        double comisionCompra = activo.getComision();
	        double rendimiento = (precioSupuesto * inversionNeta / precioCompra) - inversionNeta;
	        double capitalCompra = inversionNeta + rendimiento;
	        capitalTotal += capitalCompra;
	        rendimientoTotal += rendimiento;
	        totalComisiones += activo.getInversionBruta() * (comisionCompra / 100);
	        resultado.append("asset ").append(activo.getId()).append(":\n")
	                .append("Asset Value: ").append(precioCompra).append("\n")
	                .append("Net Investment: ").append(inversionNeta).append("\n")
	                .append("Gross Investment: ").append(activo.getInversionBruta()).append("\n")
	                .append("Returns: ").append(rendimiento).append("\n")
	                .append("Total Funds: ").append(capitalCompra).append("\n")
	                .append("Brokerage commission: ").append(comisionCompra).append("%\n")
	                .append("Date: ").append(activo.getFecha()).append("\n\n");
	    }
	    int numeroTotalCompras = activos.size();
	    double rendimientoMedio = rendimientoTotal / numeroTotalCompras;
	    double precioTotal = 0;
	    for (Activo activo : activos) {
	        precioTotal += activo.getPrecio();
	    }
	    double precioMedio = activos.isEmpty() ? 0 : precioTotal / activos.size();
	    double inversionTotalBruta = 0;
	    double inversionTotalNeta = 0;
	    for (Activo activo : activos) {
	        inversionTotalBruta += activo.getInversionBruta();
	        inversionTotalNeta += activo.getInversionNeta();
	    }
	    double rendimientoReal = capitalTotal - inversionTotalBruta;
	    resultado.append("Total Assets: ").append(numeroTotalCompras).append("\n")
	            .append("Average Assets Returns: ").append(rendimientoMedio).append("\n")
	            .append("Average Purchase Price: ").append(precioMedio).append("\n")
	            .append("Total Gross Investment: ").append(inversionTotalBruta).append("\n")
	            .append("Total Net Investment: ").append(inversionTotalNeta).append("\n")
	            .append("Total Funds: ").append(capitalTotal).append("\n")
	            .append("Net Total Returns: ").append(rendimientoTotal).append("\n")
	            .append("Real Returns: ").append(rendimientoReal).append("\n")
	            .append("Total Commissions: ").append(totalComisiones);
	    JTextArea textArea = new JTextArea(resultado.toString());
	    textArea.setEditable(false);
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    scrollPane.setPreferredSize(new Dimension(500, 400));
	    JOptionPane.showMessageDialog(this, scrollPane, "Simulation results", JOptionPane.INFORMATION_MESSAGE);
	}
	private void EliminarActivo() {
		if (activos.isEmpty()) {
			JOptionPane.showMessageDialog(this, "There are no assets to delete.");
			return;
		}
		String[] opciones = new String[activos.size()];
		for (int i = 0; i < activos.size(); i++) {
			opciones[i] = "Asset " + activos.get(i).getId();
		}
		String seleccion = (String) JOptionPane.showInputDialog(this, "Select the asset to delete:",
				"Delete asset", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
		if (seleccion == null) {
			return;
		}
		for (Activo activo : activos) {
			if (seleccion.equals("Asset " + activo.getId())) {
				activos.remove(activo);
				JOptionPane.showMessageDialog(this, "Asset deleted successfully.");
				return;
			}
		}
		JOptionPane.showMessageDialog(this, "Error: the selected asset could not be found.");
	}
	private void MostrarMostrarActivos() {
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		StringBuilder sb = new StringBuilder();
		sb.append("Total Assets: ").append(activos.size()).append("\n\n");
		for (Activo activo : activos) {
			sb.append("Asset ").append(activo.getId()).append(":\n");
			sb.append("Asset Value: ").append(activo.getPrecio()).append("\n");
			sb.append("Gross Investment: ").append(activo.getInversionBruta()).append("\n");
			sb.append("Net Investment: ").append(activo.getInversionNeta()).append("\n");
			sb.append("Brokerage commission: ").append(activo.getComision()).append("%\n");
			sb.append("Date: ").append(activo.getFecha()).append("\n\n");
		}
		textArea.setText(sb.toString());
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 400));
		JOptionPane.showMessageDialog(this, scrollPane, "Show Assets", JOptionPane.INFORMATION_MESSAGE);
	}
	private double obtenerNumeroValido(String input, String campo) {
		try {
			double numero = Double.parseDouble(input);
			if (numero <= 0) {
				throw new IllegalArgumentException(campo + " must be a positive number.");
			}
			return numero;
		} catch (NumberFormatException ex) {
			throw new NumberFormatException("Please, enter a valid numerical value for " + campo + ".");
		}
	}
	private String obtenerFechaValida(String input) {
	    String[] partesFecha = input.split("-");
	    int dia = Integer.parseInt(partesFecha[0]);
	    int mes = Integer.parseInt(partesFecha[1]);
	    int anio = Integer.parseInt(partesFecha[2]);

	    if (dia < 1 || dia > 31) {
	        throw new IllegalArgumentException("Invalid day of month. Please enter a valid date.");
	    }

	    if (mes < 1 || mes > 12) {
	        throw new IllegalArgumentException("Invalid month. Please enter a valid date.");
	    }

	    if (mes == 2) {
	        boolean esBisiesto = (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
	        int diasFebrero = esBisiesto ? 29 : 28;
	        if (dia > diasFebrero) {
	            throw new IllegalArgumentException("Invalid day for this month. Please enter a valid date.");
	        }
	    } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
	        if (dia > 30) {
	            throw new IllegalArgumentException("Invalid day for this month. Please enter a valid date.");
	        }
	    }

	    LocalDate fechaActual = LocalDate.now();
	    LocalDate fechaIngresada = LocalDate.of(anio, mes, dia);

	    if (fechaIngresada.isAfter(fechaActual)) {
	        throw new IllegalArgumentException("The entered date is after the current date. Please enter a valid date.");
	    }

	    return input;
	}


	private void centrarVentana(Window ventana) {
		Dimension dimPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dimPantalla.width - ventana.getSize().width) / 2;
		int y = (dimPantalla.height - ventana.getSize().height) / 2;
		ventana.setLocation(x, y);
	}
}
