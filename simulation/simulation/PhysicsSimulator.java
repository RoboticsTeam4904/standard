class PhysicsSimulator extends Thread {

    static PhysicsSimulator inst; // = new PhysicsSimulator().start();;


    ArrayList<Motor> motors;

    public PhysicsSimulator(ArrayList<Motor> motors) {
        self.motors = motors;
    }

    @Override
    public void run() {
        while(true) {
            for(Motor m : self.motors) {
                System.out.println(m.angularVelocity);
            }
        }   
    }
}