package Auction_Project.Auction_Server;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.hibernate.type.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Auction_Project.Auction_Server.hibernate.model.item;

public class itemAdapter implements JsonSerializer<item> {

	@Override
	public JsonElement serialize(item item, java.lang.reflect.Type type, JsonSerializationContext jsc) {
		JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("item_id", item.getItemID());
        jsonObject.addProperty("item_user_id", item.getItemUserId());
        jsonObject.addProperty("item_category", item.getItemCategory());
        jsonObject.addProperty("item_name", item.getItemName());
        jsonObject.addProperty("item_desc", item.getItemDescription());
        jsonObject.addProperty("item_picture", item.getItemPicture());
        jsonObject.addProperty("item_start_price", item.getItemStartingPrice());
        jsonObject.addProperty("item_last_bid_price", item.getItemLastBidPrice());
        jsonObject.addProperty("item_last_bid_time", item.getItem_last_bid_time());
        jsonObject.addProperty("item_last_bid_userid", item.getItemUserId());
        jsonObject.addProperty("insert_time", item.getInsert_time());
        jsonObject.addProperty("update_time", item.getUpdate_time());
        return jsonObject;
	}
}
