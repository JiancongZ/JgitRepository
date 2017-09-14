import java.util.Comparator;

class VertexComparator implements Comparator<Vertex> {
    public int compare(Vertex v1, Vertex v2) {
        if (v1.g>v2.g) return 1;
        if(v1.g<v2.g) return -1;
        return 0;
    }
} 