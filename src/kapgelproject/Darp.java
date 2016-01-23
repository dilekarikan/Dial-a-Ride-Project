
package kapgelproject;

//import groovyjarjarasm.asm.Label;
import ilog.concert.*;
import ilog.cplex.*;
import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import jdk.internal.org.objectweb.asm.Label;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Number;

public class Darp {
    
    public static void SolveMe() throws Exception{
//      Declarations
        
        
        int n = 5;// number of requests
        int K = 4; // number of couriers
        int N = (2*n+2);
        
        Date pickup[]=new Date[n];
        Date deadline[]=new Date[n];
        int h=1; // Max. ride time of a request
        double c[][][] = new double[N][N][K]; // cij Routing cos4
        double M[][][] = new double[N][N][K];
        int W[][][] = new int[N][N][K];
        int q[] = new int[N];
        double T[]= new double[K];
        int KQ[]=new int[K];
        


            
//        double coordinates[]=ExcelRead.ExcelRead("C:\\Users\\asus\\Documents\\MATLAB\\testsample.xls");
//        for (int i=0; i<coordinates.length;i++){
////            System.out.println(coordinates[i]);
//        }

        
        
        double pickup_dropoff[][]={
            {41.041999,28.961532},  //origin           
            {41.053439,28.987331}, // pickup_baslangic
            {41.110152,29.030202},
            {41.0530635,28.9892882},
            {41.050062,28.99322},
            {41.0533527,28.9882135}, // pickup_bitis
            {41.0465074756929,28.9976297533289}, // dropoff_baslangic
            {41.1120245907701,29.0355987489495},
            {41.0808553614557,28.9658823695712},
            {41.0671789266345,28.979907222684},
            {41.0750425734557,28.984906204119}, // droppoff_bitis
            {41.041999,28.961532} //destination
         
        };
        
//      Duration of service at node i         
        double d[]=new double[N];
        for (int i=0; i<N; i++){
            d[i]=0.2;
        }
      
// Each vehicle has a capacity KQ
        
        for (int k=0; k<K; k++){
            KQ[k]=10;
        }
        
         //Amount loaded a vehicle at node i 
         
         q[0]=0; 
         q[N-1]=0;
         for (int i=1; i<n+1;i++){
             q[i]=1;
         }
         for (int i=n+1;i<2*n+1; i++){
             q[i]=-1;
         }
         
         for (int i=0; i<N; i++)
         {
//             System.out.println(q[i]);
         }
        //time needed for going from node i to node j
         
        double t[][] = new double[N][N];
        
        for (int i=0; i<N; i++){
            for (int j=0; j<N; j++){
                if (i!=j){
                    t[i][j]=0.2;
                }
                else if(i==j){
                    t[i][j]=0.0;
                }                     
            }
        }
        
        // total duration of courier k cannot exceed Tk
        
        for (int k=0; k<K; k++){
            
            T[k] = 9.0;
        }
//Define pickup times array
//        Date pickuptimes[]=new Date[n];
//        String timestamp[]=ExcelRead.ExcelRead("C:\\Users\\asus\\Documents\\MATLAB\\testsample.xls");
//        for (int i=0; i<timestamp.length;i++){
////            System.out.println(timestamp[i]);
//        }
////
//        try {
//        
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//        for ( int i=0; i<n; i++){
//        pickuptimes[i] = dateFormat.parse(timestamp[i]);
//      System.out.println(dateFormat.format(pickuptimes[i]));
//        }        
        
        try {
        String[] strdate = {"2015-08-17 16:48:21", "2015-08-17 06:53:21", "2015-08-17 10:59:55","2015-08-17 15:55:16" , "2015-08-17 11:33:05", "2015-08-17 08:06:21", "2015-08-17 08:46:03", "2015-08-17 10:45:01","2015-08-17 16:20:14","2015-08-17 13:01:19"};
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for ( int i=0; i<n; i++){
            pickup[i] = dateFormat.parse(strdate[i]);
//      System.out.println(dateFormat.format(pickup[i]));
        }

        
//   Add 1 hour to the elements of pickup array in order to have deadline array
        
        Calendar calendar = Calendar.getInstance();
        
        for (int i=0; i<n; i++){
        calendar.setTime(pickup[i]);
        calendar.add(Calendar.HOUR, h);
//      System.out.println(dateFormat.format(calendar.getTime()));
        for (int ii=0; ii<n; ii++){
            Date result=calendar.getTime();
            deadline[ii]=result;
            
        }   
        }
        
        Date e[]=new Date[N];
        e[0]=pickup[0];
        e[N-1]=pickup[n-1];
        for (int i=0; i<n; i++){
            e[i+1]=pickup[i];
        }
        for (int i=0; i<n; i++){
            e[i+n+1]=pickup[i];
        }
        


        Date l[]=new Date[N];
        for (int i=0; i<N; i++){
            calendar.setTime(e[i]);
            calendar.add(Calendar.HOUR, h);
            Date result1=calendar.getTime();
            l[i]=result1;
        }

        
        
        } catch (ParseException e) {
            e.printStackTrace();

        }
        
//        Haversine distances array between the coordinate points : pickup and dropoff
    
//        Define cij routing cost
        for (int k=0; k<K; k++){
         for (int i=0; i<N; i++){
             for (int j=0; j<N; j++){
             c[i][j][k]=Haversine.haversine(pickup_dropoff[i][1], pickup_dropoff[i][0], pickup_dropoff[j][1], pickup_dropoff[j][0]);    
         }
         }
      
    }

         for (int k=0; k<K; k++){
         for (int i=0; i<N; i++){
             for (int j=0; j<N; j++){
             M[i][j][k]=3;    
         }
         }
    }

         for (int k=0; k<K; k++){
         for (int i=0; i<N; i++){
             for (int j=0; j<N; j++){
             W[i][j][k]=10;    
         }
         }
    }         
         
            try{
                
//          define new model
                
            IloCplex cplex = new IloCplex(); 
//            cplex.setOut(null);
            
//          define decision variables
   
                IloIntVar[][][] x = new IloIntVar[N][N][];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        x[i][j] = cplex.boolVarArray(K);
                        for (int k = 0; k < K; k++) {
                            x[i][j][k].setName("x." + i + "." + j + "." + k);
                        }
                    }
                }
              
                
            IloNumVar[][] B = new IloNumVar[N][];
            
            for (int i=0; i<N; i++){
                
                B[i] = cplex.numVarArray(K,0,Double.MAX_VALUE);
            }
                
            
            IloNumVar[][] Q = new IloNumVar[N][];
            
            for (int i=0; i<N; i++){
                
                Q[i] = cplex.numVarArray(K,0,Double.MAX_VALUE);
            } 
            
            
            IloNumVar[][] L = new IloNumVar[N][];
            
            for (int i=0; i<N; i++){
                
                L[i] = cplex.numVarArray(K,0,Double.MAX_VALUE);
            }
            
            
            
            //declare Objective
//            
            IloLinearNumExpr obj = cplex.linearNumExpr();
            
           for (int k=0; k<K; k++){
            
            for(int i=0; i<N; i++){
                
                for (int j=0; j<N; j++){
                    
                    if (j!=i && i!=j){
                        obj.addTerm(c[i][j][k], x[i][j][k]);
                    }
                }
            }
            
            }
            
            cplex.addMinimize(obj);
            
          
//          xij should be 1 for each i € A and when i!=j 

//            
            for (int i=1; i<n+1; i++){
                
                IloLinearNumExpr expr = cplex.linearNumExpr();
                
                for (int k=0; k<K; k++){
                    
                for (int j=0; j<N; j++){
                    
                    if(i!=j){
                        
                        expr.addTerm(1.0, x[i][j][k]);
                        
                    }
                    
                }      
                
            }
            cplex.addEq(expr, 1.0);
            }
            

            
////Same vehicle services pick up and delivery
                
            for (int i=1; i<n+1; i++){
                
                for (int k=0; k<K; k++){

                IloLinearNumExpr expr_1 = cplex.linearNumExpr();
                IloLinearNumExpr expr_2 = cplex.linearNumExpr();
                
                for (int j=0; j<N; j++){
                    
                    if(i!=j){
                        expr_1.addTerm(1.0, x[i][j][k]);
                                 
                    }
                    
                    if((n+i)!=j){
                        expr_2.addTerm(-1.0, x[n+i][j][k]);
                    }
                
                }

              cplex.addEq(cplex.sum(expr_1,expr_2), 0.0);
            } 
            }        
            
////////Route begins at origin
                 
                
                for (int k=0; k<K; k++){
                IloLinearNumExpr expr1 = cplex.linearNumExpr();
                for (int j=0; j<N; j++){
                    
                    if(j!=0){
                        
                        expr1.addTerm(1.0, x[0][j][k]);
                        
                    }
                }
                cplex.addEq(expr1, 1.0);
                }
              
                
////////Route starts with the origin and ends with the destination
                
            for (int k=0; k<K; k++){        
            for (int i=1; i<2*n+1; i++){
                
                    IloLinearNumExpr expr_1 = cplex.linearNumExpr();
                    IloLinearNumExpr expr_2 = cplex.linearNumExpr();    
                
                for (int j=0; j<N; j++){

                    if(j!=i && i!=j){
                                               
                        expr_1.addTerm(1.0, x[j][i][k]);
                        expr_2.addTerm(-1.0, x[i][j][k]);
                               
                    }

                }

              cplex.addEq(cplex.sum(expr_1,expr_2), 0.0);   
            } 
            }
            
////////Route ends at dropoff 
                for (int k=0; k<K; k++){
                IloLinearNumExpr expr2 = cplex.linearNumExpr();
                
                for (int i=0; i<N; i++){
                    
                    if(i!=2*n+1){
                        
                        expr2.addTerm(1.0, x[i][2*n+1][k]);
                    }
                }
                
                cplex.addEq(expr2, 1.0);
                }
                
// Time and load constraints 

                //Constraint 7
                
                for (int i=0; i<N; i++){
                    for (int j=0; j<N; j++){
                        for (int k=0; k<K;k++){
                           IloLinearNumExpr expr = cplex.linearNumExpr();
                           expr.addTerm(1.0,B[i][k]);
                           expr.addTerm(M[i][j][k],x[i][j][k]);
                           expr.addTerm(-1.0, B[j][k]);
                           double righthandside1 = (M[i][j][k]-d[i]-t[i][j]);
                           cplex.addLe(expr,righthandside1);
                    }
                    }
                }
                
            // Constraint 8   
            for (int i=0; i<N; i++){
                for (int j=0; j<N; j++){
                    for (int k=0; k<K; k++){
                        IloLinearNumExpr expr = cplex.linearNumExpr();
                        expr.addTerm(1.0,Q[i][k]);
                        expr.addTerm(-1.0,Q[j][k]);
                        expr.addTerm(W[i][j][k], x[i][j][k]);
                        double righthandside2=(W[i][j][k]-q[j]);
                        cplex.addLe(expr,righthandside2);
//                      
                    }
                       
                }
            }

           // Constraint 9
            
          for (int i=1; i<n+1;i++){
              for (int k=0; k<K; k++){
                  IloLinearNumExpr expr = cplex.linearNumExpr();
                  expr.addTerm(-1.0,L[i][k]);
                  expr.addTerm(1.0, B[n+i][k]);
                  expr.addTerm(-1.0,B[i][k]);
                  cplex.addEq(expr, d[i]);
              }
          }
            
          // Constraint 10 
          
          for (int k=0; k<K; k++){
              IloLinearNumExpr expr = cplex.linearNumExpr();
              expr.addTerm(1.0,B[2*n+1][k]);
              expr.addTerm(-1.0,B[0][k]);
              cplex.addLe(expr, T[k]);
          }
          
          // Constraint 11
          
//          for (int i=0; i<N; i++){
//              for (int k=0; k<K; k++){
//                  IloLinearNumExpr expr = cplex.linearNumExpr();
//                  expr.addTerm(1.0, B[i][k]);
//                  cplex.addLe(expr, l[i]);
//              }
//          }
            
          // Constraint 12
          
          for (int i=1; i<n+1; i++){
              for (int k=0; k<K; k++){
                  IloLinearNumExpr expr = cplex.linearNumExpr();
                  expr.addTerm(1.0,L[i][k]);
                  cplex.addLe(expr, h);
              }
          }
          
            for (int i = 1; i < n + 1; i++) {
                for (int k = 0; k < K; k++) {
                    IloLinearNumExpr expr = cplex.linearNumExpr();
                    expr.addTerm(-1.0, L[i][k]);
                    double righthandside3 = (-t[i][n+i]);
                    cplex.addLe(expr, righthandside3);
                    
                }
            }
                    
            //Constraint 13
            
            for (int i=0; i<N; i++){
                for (int k=0; k<K; k++){
                    IloLinearNumExpr expr = cplex.linearNumExpr();
                    expr.addTerm(1.0, Q[i][k]);
                    int lefthandside1 = java.lang.Math.max(0, q[i]);
                    int righthandside4 = java.lang.Math.min(KQ[k], KQ[k]+q[i]);
                    cplex.addGe(lefthandside1, expr);
                    cplex.addLe(expr, righthandside4);
                }
            }
            
            
            
            cplex.setParam(IloCplex.Param.Simplex.Display,0);
            
//            // solve model
            
                if (cplex.solve()) {

//                    cplex.output().println("Solution status =" + cplex.getStatus());
                    cplex.output().println("Objective value =" + cplex.getObjValue());
                    cplex.output().println("Solution status =" + cplex.getStatus());
                    for (int k = 0; k < K; k++) {
                        for (int i = 0; i < N; i++) {
                            for (int j = 0; j < N; j++) {

                                try {

                                    if (cplex.getValue(x[i][j][k]) > 0.0) {
//                                        System.out.println(x[i][j][k].getName() + " " + cplex.getValue(x[i][j][k]));
                                        System.out.println(i + " " + j + " "+ k + " " + cplex.getValue(x[i][j][k]));
//                                        System.out.println(Q[i][k].getName() + " " + cplex.getValue(Q[i][k]));
//                                        System.out.println(L[i][k].getName() + " " + cplex.getValue(L[i][k]));
                                           
                                                // Write excel 
        
                                          WritableWorkbook wworkbook;
                                          wworkbook = Workbook.createWorkbook(new File("output.xls"));
                                          WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
//                                          Label label1 = new Label(0, 0, "x.i");
//                                          Label label2 = new Label(1, 0, "x.j");
//                                          Label label3 = new Label(2, 0, "x.k");
//                                          wsheet.addCell(label1);
//                                          wsheet.addCell(label2);
//                                          wsheet.addCell(label3);
                                          Number number = new Number(0, 1, 3.1459);
                                          wsheet.addCell(number);
                                          wworkbook.write();
                                          wworkbook.close();

                                    }

//                                    System.out.println("" + i + " " + "" + j + " "+ k + " " + cplex.getValue(x[i][j][k]));

                                } catch (IloException ignore) {
                                }
                            }
                        }

                    }
                }
                
                else{
                    cplex.output().println("Solution status =" + cplex.getStatus());
                }
      
            cplex.exportModel("lpex1.lp");
            
            
            // end model to release the memory
            
            cplex.end();
            

            }
            catch (IloException exc){
            exc.printStackTrace();
            
    }
    
}
}