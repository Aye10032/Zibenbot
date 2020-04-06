package com.aye10032.Utils.fangzhoudiaoluo;

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

    public static class Material {
        public String id = "";
        public int tier;
        public String name = "";
        public String credit_store_value = "";
        public String green_ticket_value = "";
        public String golden_ticket_value= "";
        public String type = "";
        public Stage[] lowest_ap_stages = null;
        public Stage[] balanced_stages = null;
        public Stage[] drop_rate_first_stages = null;

        public Material(){}

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

        public HeChenType(String id, String[] names, String[] calls) {
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
            this.names = list.toArray(new String[]{});
            ;
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

    public static class Node<E, N> {

        E event;
        N normal;

        public E getEvent() {
            return event;
        }

        public N getNormal() {
            return normal;
        }
    }

}
