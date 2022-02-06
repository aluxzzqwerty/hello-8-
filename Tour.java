public class Tour {

    private static class Node {
        private Point p;
        private Node next;

        public Node(Point p) {
            this.p = p;
            this.next = null;
        }

        public Node(Point p, Node next) {
            this.p = p;
            this.next = next;
        }
    }

    private Node start;

    // creates an empty tour
    public Tour(Node s) {
        this.start = s;
    }

    // creates the 4-point tour a->b->c->d->a (for debugging)
    public Tour(Point a, Point b, Point c, Point d) {
        start = new Node(a);
        Node newNode2 = new Node(b);
        Node newNode3 = new Node(c);
        Node newNode4 = new Node(d);

        start.next = newNode2;
        newNode2.next = newNode3;
        newNode3.next = newNode4;
        newNode4.next = start;
    }

    // returns the number of points in this tour
    public int size() {
        Node current = start;
        int size = 0;
        do {
            current = current.next;
            size++;
        } while (current != start);
        return size;
    }


    // returns the length of this tour
    public double length() {
        if (start == null) return 0.0;
        Node x = start;
        double total = 0.0;
        do {
            total += x.p.distanceTo(x.next.p);
            x = x.next;
        } while (x != start);
        return total;
    }

    // returns a string representation of this tour
    public String toString() {
        Node current = start;
        StringBuilder str = new StringBuilder();
        do {
            str.append(current.p.toString() + "\n");
            current = current.next;
        } while (current != start);
        return str.toString();
    }


    // draws this tour to standard drawing
    public void draw() {
        if (start == null) return;
        Node x = start;
        do {
            if (x.next != null) {
                x.p.drawTo(x.next.p);
                x = x.next;
            }
        } while (x != start);
    }

    public void insertNearest(Point p) {
        Node temp = start;
        Node minNode = null;

        double bestDist = Double.MAX_VALUE;
        
        for (int i = 0; i < size(); i++) {
            double distance = p.distanceTo(temp.p);
            if (distance < bestDist) {
                bestDist = distance;
                minNode = temp;
            }

            temp = temp.next;
        }

        if (minNode == null) {
            start = new Node(p);
            start.next = start;
        }
        else {
            minNode.next = new Node(p, minNode.next);
        }
    }

    // inserts p using the smallest increase heuristic
    public void insertSmallest(Point p) {
        double bestDist = Double.MAX_VALUE;
        Node temp = start;
        Node minNode = null;

        for (int i = 0; i < size(); i++) {
            double tempToNewDist = temp.p.distanceTo(p);
            double newToNextDist = p.distanceTo(temp.next.p);
            double tempToNextDist = temp.p.distanceTo(temp.next.p);
            double delta = tempToNewDist + newToNextDist - tempToNextDist;
            if (delta < bestDist) {
                bestDist = delta;
                minNode = temp;
            }

            temp = temp.next;
        }

        if (minNode == null) {
            start = new Node(p);
            start.next = start;
        }
        else {
            minNode.next = new Node(p, minNode.next);
        }

    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);

        // define 4 points, corners of a square
        Point a = new Point(1.0, 1.0);
        Point b = new Point(1.0, 4.0);
        Point c = new Point(4.0, 4.0);
        Point d = new Point(4.0, 1.0);

        // create the tour a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, b, c, d);

        int size = squareTour.size();
        StdOut.println("# of points = " + size);

        // print the tour length to standard output
        double length = squareTour.length();
        StdOut.println("Tour length = " + length);

        System.out.println(squareTour);

        Point e = new Point(5.0, 6.0);
        squareTour.insertSmallest(e);
        squareTour.draw();

    }
}





