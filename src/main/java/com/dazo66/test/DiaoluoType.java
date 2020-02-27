package com.dazo66.test;

/**
 * @author Dazo66
 */
public class DiaoluoType {

    public Material[] material;

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

}
