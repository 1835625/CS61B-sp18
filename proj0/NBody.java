public class NBody {
    public static double readRadius(String s) {
        In in = new In(s);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String s) {
        In in = new In(s);
        int numPlanets = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[numPlanets];
        for(int i = 0; i < numPlanets; i += 1) {
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }
/**
    public static double Try(String s) {
        In in = new In(s);
        int numPlanets = in.readInt();
        in.readDouble();
        Planet p = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        return p.xxPos;
    }

    public static void main(String[] args) {
        String s = "./data/planets.txt";
        readPlanets(s);
    }*/
}