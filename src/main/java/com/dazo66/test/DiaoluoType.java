package com.dazo66.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dazo66
 */
public class DiaoluoType {

    public Material[] material;

    public Material getMaterialFromID(String id) {
        if (material != null) {
            for (Material material : this.material) {
                if (material.id.equals(id)) {
                    return material;
                }
            }
        }
        return null;
    }

    public class Material {
        public String _id;
        public String id;
        public int tier;
        public String name;
        public float credit_store_value;
        public float green_ticket_value;
        public float golden_ticket_value;
        public String type;
        public int Order_id;
        public String Notes;
        public Stage[] lowest_ap_stages;
        public Stage[] balanced_stages;
        public Stage[] drop_rate_first_stages;
        public String last_updated;

    }

    public class Stage {
        public Drop[] extra_drop;
        public String code;
        public float drop_rate;
        public float efficiency;
        public float ap_per_item;
    }

    public class Drop {
        public String name;
        public int id;
    }

    public static class HeChenType {
        public String id;
        public String[] names;
        public String[] calls;

        public HeChenType(String id, String[] names, String[] calls){
            this.id = id;
            List<String> list = new ArrayList<>();
            for (String c : calls) {
                list.add(c.trim());
            }
            this.calls = list.toArray(new String[]{});
            list = new ArrayList<>();
            for (String c : names) {
                list.add(c.trim());
            }
            this.names = list.toArray(new String[]{});;
        }

        public boolean isThis(String name) {
            for (String s : names) {
                if (s.trim().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }

}
