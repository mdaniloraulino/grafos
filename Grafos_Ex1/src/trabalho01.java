import java.util.ArrayList;

import javax.swing.JOptionPane;

public class trabalho01 {

	private static String input;
	private static ArrayList<Aresta> arestas = new ArrayList<Aresta>();
	private static ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private static boolean valorado = false;
	private static boolean orientado = false;
	private static boolean diferentao = false;
	public static void main(String[] args) {
		start();
	}

	private static void start() {
		
		if (JOptionPane.showConfirmDialog(null, "Grafo será Valorado?") == 0) {
			valorado = true;
		}
		if (JOptionPane.showConfirmDialog(null, "Grafo será Orientado?") ==0) {
			orientado = true;
		}
//		if (valorado && orientado) {
//			if (JOptionPane.showConfirmDialog(null, "Para Matriz de incidencias valorada e Orientada \n "
//					+ "Caso haja mais de uma aresta nos mesmos pontos gostaria de selecionar o Maior valor?"
//					+ "\n Caso queira o menos ignore esta mensagem") ==0) {
//				diferentao = true;
//			}	
//		}
		
		input = JOptionPane.showInputDialog(null);
		String[] arestasTemp = input.split("/");
		String[] valores;
		for (String i : arestasTemp) {
			valores = i.split(",");

			Vertice v = new Vertice();
			Vertice v2 = new Vertice();
			v.setNome(Double.parseDouble(valores[0]));
			v2.setNome(Double.parseDouble(valores[1]));
			
			Aresta a = valorado ? new Aresta(v, v2,Double.parseDouble(valores[2])) : new Aresta(v,v2,0);
			
			if (!ContainsAresta(a, true)) {
				arestas.add(a);
			}
			if (vertices.isEmpty()) {
				vertices.add(v);
				vertices.add(v2);
			} else {
				
				if(!containsVertice(v)) {
					vertices.add(v);
				}
				if(!containsVertice(v2)) {
					vertices.add(v2);
				}
			}
		}
		ListaAdjacencia();
		MostraRepresentacoes();
	}
	
	private static boolean containsVertice(Vertice v){
		for ( Vertice vt : vertices) {
			if(vt.getNome() == v.getNome()) {
				return true;
			}
		}
		return false;
	}

	private static boolean VerificaIgualdade(Vertice v1, Vertice v2) {

		if (v1.getNome() == v2.getNome())
			return true;
		else
			return false;
	}
	//WTF
	private static void MostraRepresentacoes() {
		String a = "Lista de Arestas \n";
		int[][] matrizAdj;
		int[][] matrizInc;
		for (Aresta aresta : arestas) {
			a += "{" + aresta.getV1().getNome() + "," + aresta.getV2().getNome() + "}\n";
		}
		a += "Lista de Adjacencia \n";

		for (Vertice v : vertices) {
			a += "[" + v.getNome() + "]" + "-";
			for (Vertice va : v.getAdjacentes()) {
				a += va.getNome() + " | ";
			}
			a += "\n";
		}
		
		
		matrizAdj = MatrizAdjacencia();

		a += " \n-------------\n Matriz adjacencia \n & = 9999 \n         ";
		for (Vertice v : vertices) {
			a += "[" + v.getNome() + "] ";
		}
		a += "\n";

		for (int i = 0; i < vertices.size(); i++) {
			a += "[" + vertices.get(i).getNome() + "]   ";
			for (int j = 0; j < vertices.size(); j++) {
				if(matrizAdj[i][j] != 9999) {
					a += matrizAdj[i][j] + "       ";
				}else {
					a += "&       ";
				}
			}
			a += "\n";
		}
		a += "\n";

		a += " \n-------------\n Matriz Incidência \n & = 9999 \n         ";
		for (Aresta ares : arestas) {
			a += "  {" + ares.getV1().getNome() + "|" + ares.getV2().getNome() + "}   ";
		}
		a += "\n";
		matrizInc = MatrizIncidencia();
		for (int i = 0; i < vertices.size(); i++) {
			a += "[" + vertices.get(i).getNome() + "]   ";
			for (int j = 0; j < arestas.size(); j++) {
				if(matrizInc[i][j] != 9999) {
					a += "       " + matrizInc[i][j] + "           ";
				}else {
					a += "       " + "&" + "           ";
				}
				
			}
			a += "\n";
		}

		JOptionPane.showMessageDialog(null, a);
	}

	private static void ListaAdjacencia() {
		for (Vertice v : vertices) {
			for (Aresta a : arestas) {
				if (!orientado) {
					if (VerificaIgualdade(a.getV1(), v)) {
						if (!containsVerticeAdj(v, a.getV2())) {
							v.getAdjacentes().add(a.getV2());
						}
					} else if (VerificaIgualdade(a.getV2(), v)) {
						if (!containsVerticeAdj(v, a.getV1())) {
							v.getAdjacentes().add(a.getV1());
						}
					}
				} else {
					if (VerificaIgualdade(a.getV1(), v)) {
						if (!containsVerticeAdj(v, a.getV2())) {
							v.getAdjacentes().add(a.getV2());
						}
					}
				}
			}
		}
	}

	private static int[][] MatrizIncidencia() {
		int[][] matrizInc = new int[vertices.size()][arestas.size()];

		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < arestas.size(); j++) {
				if (!orientado) {
					if (VerificaIgualdade(vertices.get(i), arestas.get(j).getV1())
							| VerificaIgualdade(vertices.get(i), arestas.get(j).getV2())) {
						// if(arestas.get(j).getOcorr() != 1) {
						if(!valorado) {
							matrizInc[i][j] += arestas.get(j).getOcorr();
						}else {
							matrizInc[i][j] += arestas.get(j).getValor();
						}
						
						// }else {
						// matrizInc[i][j] = 1;
						// }

					}
				}else {
					if (VerificaIgualdade(vertices.get(i), arestas.get(j).getV1())) {
						if(!valorado) {
							matrizInc[i][j] += arestas.get(j).getOcorr();
						} else {
							matrizInc[i][j] += arestas.get(j).getValor();
						}
					} else if (VerificaIgualdade(vertices.get(i), arestas.get(j).getV2())) {
						if(!valorado) {
							matrizInc[i][j] += -1*arestas.get(j).getOcorr();
						} else {
							matrizInc[i][j] += -1*arestas.get(j).getValor();
						}
						
					//	matrizInc[i][j] += -1*(valorado ? arestas.get(j).getValor() : arestas.get(j).getOcorr(),);
					}else {
						matrizInc[i][j] += valorado ? 9999 : 0;
					}
				}
				
			}
		}

		return matrizInc;
	}

	private static int[][] MatrizAdjacencia() {
		int[][] matrizAdj = new int[vertices.size()][vertices.size()];

		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size(); j++) {
				Aresta a1 = new Aresta(vertices.get(i), vertices.get(j),0);
				if (ContainsAresta(a1, false)) {
					if(!valorado) {
						matrizAdj[i][j] = 1;
					}else {
						matrizAdj[i][j] = (int)a1.getValor();
					}
				} else {
					if(!valorado) {
						matrizAdj[i][j] = 0;
					}else {
						matrizAdj[i][j] = 9999;
					}
				}
			}
		}

		return matrizAdj;
	}

	private static boolean ContainsAresta(Aresta a1, Boolean addOcorr) {

		for (Aresta aresta : arestas) {
			if (VerificaIgualdadeAresta(a1, aresta)) {
				if (addOcorr) {
					aresta.setOcorr(aresta.getOcorr() + 1);
				}
				if(valorado) {
					a1.setValor(aresta.getValor());
				}
				return true;
			}
		}
		return false;
	}

	private static boolean VerificaIgualdadeAresta(Aresta a1, Aresta a2) {
		if (!orientado) {
			if ((VerificaIgualdade(a1.getV1(), a2.getV1()) && VerificaIgualdade(a1.getV2(), a2.getV2()))
					| (VerificaIgualdade(a1.getV1(), a2.getV2()) && VerificaIgualdade(a1.getV2(), a2.getV1()))) {
				return true;
			}
			return false;
		} else {
			if ((VerificaIgualdade(a1.getV1(), a2.getV1()) && VerificaIgualdade(a1.getV2(), a2.getV2()))) {
				return true;
			}
			return false;
		}
	}

	private static boolean containsVerticeAdj(Vertice v, Vertice verificar) {

		for (Vertice vert : v.getAdjacentes()) {
			if (vert.getNome() == verificar.getNome()) {
				return true;
			}
		}

		return false;
	}

	private static class Vertice {
		private double nome;
		private ArrayList<Vertice> adjacentes = new ArrayList<Vertice>();

		public ArrayList<Vertice> getAdjacentes() {
			return adjacentes;
		}

		public void setAdjacentes(ArrayList<Vertice> adjacentes) {
			this.adjacentes = adjacentes;
		}

		public double getNome() {
			return nome;
		}

		public void setNome(double nome) {
			this.nome = nome;
		}

	}

	private static class Aresta {
		private Vertice v1, v2;
		private double valor;
		private int ocorr = 1;

		public Aresta(Vertice ver1, Vertice ver2,double val) {
			v1 = ver1;
			v2 = ver2;
			valor = val;
			if (v1.getNome() == v2.getNome()) {
				ocorr = 2;
			}
		}

		public Vertice getV1() {
			return v1;
		}

		public int getOcorr() {
			return ocorr;
		}

		public void setOcorr(int ocorr) {
			this.ocorr = ocorr;
		}

		public void setV1(Vertice v1) {
			this.v1 = v1;
		}

		public Vertice getV2() {
			return v2;
		}

		public void setV2(Vertice v2) {
			this.v2 = v2;
		}

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}

	}

}
