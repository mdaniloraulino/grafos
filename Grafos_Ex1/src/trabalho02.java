import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JOptionPane;

public class trabalho02 {
	

	private static String input;
	private static ArrayList<Aresta> arestas = new ArrayList<Aresta>();
	private static ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private static boolean valorado = true;
	private static boolean orientado = false;

	public static void main(String[] args) {
		start();
	}

	private static void start() {

		if (JOptionPane.showConfirmDialog(null, "Grafo ser� Orientado?") == 0) {
			orientado = true;
		}

		input = JOptionPane.showInputDialog(null);
		String[] arestasTemp = input.split("/");
		String[] valores;
		for (String i : arestasTemp) {
			valores = i.split(",");

			Vertice v = new Vertice();
			Vertice v2 = new Vertice();
			v.setNome(Double.parseDouble(valores[0]));
			v2.setNome(Double.parseDouble(valores[1]));

			Aresta a = valorado ? new Aresta(v, v2, Double.parseDouble(valores[2])) : new Aresta(v, v2, 0);

			if (!ContainsAresta(a, true)) {
				arestas.add(a);
			}
			if (vertices.isEmpty()) {
				vertices.add(v);
				vertices.add(v2);
			} else {

				if (!containsVertice(v)) {
					vertices.add(v);
				}
				if (!containsVertice(v2)) {
					vertices.add(v2);
				}
			}
		}
		ListaAdjacencia();
		menoresCaminhos();
	}

	private static boolean containsVerticeAdj(Vertice v, Vertice verificar) {

		for (Vertice vert : v.getAdjacentes()) {
			if (vert.getNome() == verificar.getNome()) {
				return true;
			}
		}

		return false;
	}

	private static void menoresCaminhos() {
		double nVer = Double.parseDouble(JOptionPane.showInputDialog("Indique o vertice de Origem: "));
		Vertice origem = getVertice(nVer);
		dijkstra(vertices, origem);
		String str = "Distancia entra a Origem : " + nVer + " e os outros vertives \n\n";

		for (Vertice v : vertices) {
			str += origem.getNome() + " - " + v.getNome() + " = ";
			Vertice ant = v.getAntecede();
			ArrayList<Vertice> caminho = new ArrayList<Vertice>();
			if (v.getNome() == origem.getNome()) {
				str += "ORIGEM\n";
			} else {
				do {
					caminho.add(ant);
					ant = ant.getAntecede();
				} while (ant != null);

				for (int i = caminho.size(); i > 0; i--) {
					str += caminho.get(i - 1).getNome() + " , ";
				}
				str += v.getNome()+" - Custo de : "+v.dist+" \n";
			}
		}
		
		JOptionPane.showMessageDialog(null, str);
	}

	private static Vertice getVertice(Double nome) {
		for (int i = 0;i < vertices.size(); i++) {
			if (vertices.get(i).getNome() == nome) {
				return vertices.get(i);
			}
		}
		return null;
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

	private static void dijkstra(ArrayList<Vertice> grafo, Vertice Source) {
		for (Vertice v : grafo) {
			v.setDist(Double.POSITIVE_INFINITY);
			v.setAntecede(null);
		}
		Source.setDist(0);
		Collections.sort(grafo);
		ArrayList<Vertice> ordenado = new ArrayList<Vertice>();
		for (Vertice v : grafo) {
			ordenado.add(new Vertice(v));
		}

		while (!ordenado.isEmpty()) {
			Vertice u = ordenado.get(0);
			ordenado.remove(u);
			for (Vertice v : u.getAdjacentes()) {
				Vertice refV = getVertice(v.getNome());
				Vertice refU = getVertice(u.getNome());
				double dist = refU.getDist() + getAresta(u, v).getValor();
				if (dist < refV.getDist()) {
					refV.setDist(dist);
					refV.setAntecede(getVertice(u.getNome()));
					
				}
			}
			Collections.sort(ordenado);
		}
		
	}

	private static Aresta getAresta(Vertice v1, Vertice v2) {
		Aresta verificar = new Aresta(v1, v2, 0);
		
			for (Aresta aresta : arestas) {
				if (VerificaIgualdadeAresta(aresta,verificar)) {
					return aresta;
				}
			}
		return null;
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

	private static boolean ContainsAresta(Aresta a1, Boolean addOcorr) {

		for (Aresta aresta : arestas) {
			if (VerificaIgualdadeAresta(a1, aresta)) {
				if (addOcorr) {
					aresta.setOcorr(aresta.getOcorr() + 1);
				}
				if (valorado) {
					a1.setValor(aresta.getValor());
				}
				return true;
			}
		}
		return false;
	}

	private static boolean containsVertice(Vertice v) {
		for (Vertice vt : vertices) {
			if (vt.getNome() == v.getNome()) {
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

	private static class Vertice implements Comparable<Vertice> {
		private double nome;
		private double dist;
		private Vertice antecede;
		private ArrayList<Vertice> adjacentes = new ArrayList<Vertice>();

		public Vertice(Vertice clone) {
			nome = clone.getNome();
			dist = clone.getDist();
			antecede = clone.getAntecede();
			adjacentes = clone.getAdjacentes();
		}

		public Vertice() {

		}

		public double getDist() {
			return dist;
		}

		public void setDist(double dist) {
			this.dist = dist;
		}

		public Vertice getAntecede() {
			return antecede;
		}

		public void setAntecede(Vertice antecede) {
			this.antecede = antecede;
		}

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

		@Override
		public int compareTo(Vertice o) {
			if (this.dist < o.getDist()) {
				return -1;
			}
			if (this.dist > o.getDist()) {
				return 1;
			}

			return 0;
		}

	}

	private static class Aresta {
		private Vertice v1, v2;
		private double valor;
		private int ocorr = 1;

		public Aresta(Vertice ver1, Vertice ver2, double val) {
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
