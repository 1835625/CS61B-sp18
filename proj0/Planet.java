public class Planet {
    static final double G = 6.67e-11;
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        return Math.sqrt(Math.pow((p.xxPos - xxPos), 2) + Math.pow((p.yyPos - yyPos), 2));
    }

    public double calcForceExertedBy(Planet p) {
        double Distance = this.calcDistance(p);
        return G * mass * p.mass / Math.pow(Distance, 2);
    }

    public double calcForceExertedByX(Planet p) {
        double dx = p.xxPos - xxPos;
        double Distance = this.calcDistance(p);
        double Force = this.calcForceExertedBy(p);
        return Force * dx / Distance;
    }

    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - yyPos;
        double Distance = this.calcDistance(p);
        double Force = this.calcForceExertedBy(p);
        return Force * dy / Distance;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double NetForceX = 0;
        for(Planet p : planets) {
            if(!this.equals(p)) {
                NetForceX += this.calcForceExertedByX(p);
            }
        }
        return NetForceX;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double NetForceY = 0;
        for(Planet p : planets) {
            if(!this.equals(p)) {
                NetForceY += this.calcForceExertedByY(p);
            }
        }
        return NetForceY;
    }
}