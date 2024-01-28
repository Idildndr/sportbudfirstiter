package com.example.sportbud3

//import com.example.sportbud.Model.RoomListItem

object Constants {
    const val BASE_URL:String ="http://164.90.184.39:9999"
    const val END_POINT_GET:String = "/activities"
    const val END_POINT_POST:String = "/activities"
    const val END_POINT_PROFILE:String ="/profiles"
    const val END_POINT_CHAT :String ="/activitieschats"
    const val END_POINT_JOIN :String ="/activities/"
    const val END_POINT_Vote :String ="/profiles/evaluations/"

  //  lateinit var ArrayList:ArrayList<RoomListItem>
  //  lateinit var RoomListGlobalArrayList:ArrayList<RoomListItem>

 //   lateinit var userArrayList: ArrayList<String>

  //  const val END_POINT_POST:String   = "/activities"

    object GlobalData {
        var userID: String = ""
        var activityID: String = ""
        var userNickname : String = ""
        var admin_id :String = ""
        var room_id:String = ""
        var userName:String= ""
        var userLastName:String = ""
        var about:String = ""
        lateinit var fav_arrayList: ArrayList<String>
        lateinit var loyalty: ArrayList<String>
        lateinit var perf: ArrayList<String>
        lateinit var fair: ArrayList<String>
    }
}