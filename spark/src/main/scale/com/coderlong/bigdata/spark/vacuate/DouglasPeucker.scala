package com.coderlong.bigdata.spark.vacuate

import com.alibaba.fastjson.JSONObject

import scala.collection.mutable.ArrayBuffer

/**
 * Implementation of Douglas-Peucker algorithm
 * This is used to simpify the original trajectory
 * Created by liyang
 */
object DouglasPeucker{
  def main(args: Array[String]): Unit = {
    val gpsArray = mockData()
    val vacuateGps = reduction(gpsArray, 20000)
    println(vacuateGps)

  }

  def mockData():Array[JSONObject]= {
    var gpsData = ArrayBuffer[JSONObject]()
    val baseLng = 115891876
    val baseLat = 39034245
    val baseTimestamp = System.currentTimeMillis()

    for(i <- 1 to 1000000){
      val gps = new JSONObject()
      gps.put("lng", baseLng + i)
      gps.put("lat", baseLat + i)
      gps.put("date_time", baseTimestamp + (i * 1000))
      gpsData += gps
    }
    gpsData.toArray
  }



//  def reduction(traj :Trajectory,bound:Double= 50):Trajectory = {
//    val gPSPoint = traj.GPSPoints
//    val list = reduction(gPSPoint.toArray,bound)
//    traj.GPSPoints = list
//    traj
//  }
   def reduction(GPSPoints : Array[JSONObject], bound:Double ):List[JSONObject] = {
    val first = GPSPoints.head
    val last = GPSPoints.last
    var points = ArrayBuffer[JSONObject]()
    points += first
    val middle = compress(GPSPoints,0,GPSPoints.length-1,bound)
    points = points ++ middle
    points += last
    points.toList
  }
  /**
   * Compress the data using Douglas-Peucker algorithm.
   * @param GPSPoints Points to be compressed
   * @param low  low bound
   * @param high  upper bound
   * @param bound Geographic coordinates offset.
   * @return all the split points in the array
   */
   def compress(GPSPoints : Array[JSONObject],low: Int,high:Int,bound: Double):Array[JSONObject] = {
    if(low < high -1){                                          //If there are more than 3 point including the first and last points, else return empty Array
      val first = GPSPoints(low)
      val last = GPSPoints(high)
      var splitPoints: ArrayBuffer[JSONObject] = ArrayBuffer[JSONObject]()
      val distance = getDistance(first,last)   //Calculate the distance between the first and the last points. This would be used in latter iterations
      var maxHeight = 0.0
      var index = low + 1

      for (i <- low+1 until high) {                                   //Iterate over the points to get the max height
        val height = getVerticalHeight(first,GPSPoints(i) , last, distance)
        if (height > maxHeight) {
          maxHeight = height
          index = i
        }
      }

      if (maxHeight > bound) {
        splitPoints = splitPoints ++ compress(GPSPoints, low, index, bound)
        splitPoints += GPSPoints(index)                                 //Add the dividing point in the result array
        splitPoints = splitPoints ++ compress(GPSPoints, index, high, bound)
      }
      splitPoints.toArray
    }else{
      Array[JSONObject]()
    }
  }

  /**
   * Get the distance between two points
   * @param p1
   * @param p2
   * @return distance in kilometer
   */
  private def getDistance(p1: JSONObject,p2:JSONObject):Double = {
    val lat1 = (Math.PI/180)*p1.getDouble("lat")/1000000
    val lat2 = (Math.PI/180)*p2.getDouble("lat")/1000000
    val lon1 = (Math.PI/180)*p1.getDouble("lng")/1000000
    val lon2 = (Math.PI/180)*p2.getDouble("lng")/1000000
    val R = 6371
    val distance = Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R
    distance
  }


  private def getVerticalHeight(first: JSONObject,p2:JSONObject,last:JSONObject,first2Last:Double):Double = {
     val edge12 = getDistance(first, p2)
     val edge23 = getDistance(last, p2)
     val edge13 = first2Last
     val p = (edge12 + edge13 + edge23)

    val area = Math.sqrt(p*(p-edge12)*(p-edge23)*(p-edge13))
    2000*area/first2Last
  }
}
