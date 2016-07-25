import CompGeom.*;

/** Implements the dynamic programming algorithm for 
minimum convex decomposition of a simple polygon. */
public class MCD extends PointIOApplet {

  public static void main(String args[]) {
    makeStandalone("Min convex decomp of simple polygon", new MCD(), 450,350); 
  }


  /** Assumes that the point list contains at least three points */
  public Anim2D compute(PointList pl) {
    int type;
    int i, k, n = pl.number();
    DecompPoly dp = new DecompPoly(pl);
    animate(dp);
    dp.init();

    for (int l = 3; l < n; l++) {
      for (i = dp.reflexIter(); i + l < n; i = dp.reflexNext(i))
	if (dp.visible(i, k = i+l)) {
	  dp.initPairs(i, k);
	  if (dp.reflex(k)) 
	    for (int j = i+1; j < k; j++) dp.typeA(i, j, k);
	  else {
	    for (int j = dp.reflexIter(i+1); j < k-1; j = dp.reflexNext(j)) 
	      dp.typeA(i, j, k);
	    dp.typeA(i, k-1, k); // do this, reflex or not.
	  }
	}
      
      for (k = dp.reflexIter(l); k < n; k = dp.reflexNext(k)) 
	if ((!dp.reflex(i = k-l)) && dp.visible(i, k)) {
	  dp.initPairs(i, k);
	  dp.typeB(i, i+1, k); // do this, reflex or not.
	  for (int j = dp.reflexIter(i+2); j < k; j = dp.reflexNext(j)) 
	    dp.typeB(i, j, k);
	}
      }
    dp.guard = 3*n;
    dp.recoverSolution(0, n-1);
    return dp;
  }
}
