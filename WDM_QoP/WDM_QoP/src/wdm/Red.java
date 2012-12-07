package wdm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Red {
	private final HashMap<String, Nodo> nodos = new HashMap<String, Nodo>();
	private final HashSet<CanalOptico> canales = new HashSet<CanalOptico>();
	
	public Red(){
		nodos.clear();
	}

	public boolean containsKey(String label) {
		return nodos.containsKey(label);
	}

	public boolean containsValue(Nodo nodo) {
		return nodos.containsValue(nodo);
	}

	public Nodo get(String key) {
		return nodos.get(key);
	}

	public Nodo put(String key, Nodo value) {
		return nodos.put(key, value);
	}

	public Nodo remove(String key) {
		return nodos.remove(key);
	}

	public int size() {
		return nodos.size();
	}

	public Collection<Nodo> values() {
		return nodos.values();
	}
	
}
