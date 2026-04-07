
import java.util.*;

class Area {
    String name;
    int urgency;
    int distance;
    String resourceNeeded;
    int populationAffected;
    boolean isAccessible;
    String disasterType;
    String contactPerson;
    long contactNumber;
    String resourceType;
    int resourceQty;

    public Area(String name, int urgency, int distance, String resourceNeeded,
            int populationAffected, boolean isAccessible, String disasterType,
            String contactPerson, long contactNumber) {
        this.name = name;
        this.urgency = urgency;
        this.distance = distance;
        this.resourceNeeded = resourceNeeded;
        this.populationAffected = populationAffected;
        this.isAccessible = isAccessible;
        this.disasterType = disasterType;
        this.contactPerson = contactPerson;
        this.contactNumber = contactNumber;
        this.resourceType = "";
        this.resourceQty = 0;
    }

    public void display() {
        System.out.printf("%-12s %-9d %-8d %-15s\n", name, distance, urgency, resourceType);
    }

    @Override
    public String toString() {
        return name + " | Urgency: " + urgency + " | Distance: " + distance + " km" +
                " | Need: " + resourceType + " | People: " + populationAffected +
                " | Access: " + (isAccessible ? "Yes" : "No") +
                " | Disaster: " + disasterType +
                " | Contact: " + contactPerson + " (" + contactNumber + ")";
    }
}

class AreaInputModule {
    public static String normalizeResourceType(String input) {
        if (input.equalsIgnoreCase("medical kit") || input.equalsIgnoreCase("medical kits"))
            return "Medical Kits";
        else if (input.equalsIgnoreCase("food packet") || input.equalsIgnoreCase("food packets"))
            return "Food Packets";
        else if (input.equalsIgnoreCase("blanket") || input.equalsIgnoreCase("blankets"))
            return "Blankets";
        else if (input.equalsIgnoreCase("water bottle") || input.equalsIgnoreCase("water bottles"))
            return "Water Bottles";
        else
            return null;
    }

    public ArrayList<Area> getAreaData() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Area> areaList = new ArrayList<>();
        System.out.println("\n===== AREA INPUT MODULE =====\n");
        System.out.print("Enter number of affected areas: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Enter details for Area " + (i + 1) + " ---\n");
            System.out.print("Area Name: ");
            String name = sc.nextLine();
            System.out.print("Disaster Type (Flood/Earthquake/Fire/etc): ");
            String disasterType = sc.nextLine();
            System.out.print("Urgency (1 = High, 2 = Medium, 3 = Low): ");
            int urgency = sc.nextInt();
            System.out.print("Distance from warehouse (in km): ");
            int distance = sc.nextInt();
            System.out.print("People affected: ");
            int populationAffected = sc.nextInt();
            sc.nextLine();

            String resourceInput;
            String normalizedResourceType;
            do {
                System.out.print("Enter Resource Type (Medical Kits / Food Packets / Blankets / Water Bottles): ");
                resourceInput = sc.nextLine().trim();
                normalizedResourceType = normalizeResourceType(resourceInput);
                if (normalizedResourceType == null) {
                    System.out.println("Invalid resource type! Please enter one of the specified types.");
                }
            } while (normalizedResourceType == null);

            System.out.print("Is area accessible by road? (true/false): ");
            boolean isAccessible = sc.nextBoolean();
            sc.nextLine();
            System.out.print("Name of the person to contact : ");
            String contactPerson = sc.nextLine();
            System.out.print("Contact Number: ");
            long contactNumber = sc.nextLong();
            sc.nextLine();
            System.out.print("Enter quantity of " + normalizedResourceType + " needed: ");
            int qty = sc.nextInt();
            sc.nextLine();

            Area newArea = new Area(name, urgency, distance, normalizedResourceType,
                    populationAffected, isAccessible, disasterType,
                    contactPerson, contactNumber);
            newArea.resourceType = normalizedResourceType;
            newArea.resourceQty = qty;
            areaList.add(newArea);
            System.out.println("\nArea " + name + " added successfully!");
        }
        System.out.println("\nTotal Areas Recorded: " + areaList.size());
        return areaList;
    }
}

class AreaStatusEntry {
    String areaName;
    String status;

    public AreaStatusEntry(String areaName, String status) {
        this.areaName = areaName;
        this.status = status;
    }
}

class MyHashTable {
    private final int SIZE = 50;
    private AreaStatusEntry[] table;

    public MyHashTable() {
        table = new AreaStatusEntry[SIZE];
    }

    private int getHash(String key) {
        int sum = 0;
        for (int i = 0; i < key.length(); i++)
            sum += key.charAt(i);
        return sum % SIZE;
    }

    public void put(String key, String value) {
        int hash = getHash(key);
        for (int i = 0; i < SIZE; i++) {
            int index = (hash + i) % SIZE;
            if (table[index] == null) {
                table[index] = new AreaStatusEntry(key, value);
                return;
            }
            if (table[index].areaName.equals(key)) {
                table[index].status = value;
                return;
            }
        }
        System.out.println("\nError: Hash table full, cannot insert " + key);
    }

    public String get(String key) {
        int hash = getHash(key);
        for (int i = 0; i < SIZE; i++) {
            int index = (hash + i) % SIZE;
            if (table[index] == null)
                return null;
            if (table[index].areaName.equals(key))
                return table[index].status;
        }
        return null;
    }

    public void displayAll() {
        System.out.println("\n=== FINAL DELIVERY REPORT ===\n");
        System.out.println("Delivered Areas:\n");
        boolean noneDelivered = true;
        for (int i = 0; i < SIZE; i++) {
            if (table[i] != null && table[i].status.equals("Delivered")) {
                System.out.println("- " + table[i].areaName);
                noneDelivered = false;
            }
        }
        if (noneDelivered) {
            System.out.println("\nNone");
        }

        System.out.println("\nPending Areas:\n");
        boolean nonePending = true;
        for (int i = 0; i < SIZE; i++) {
            if (table[i] != null && table[i].status.equals("Pending")) {
                System.out.println("- " + table[i].areaName);
                nonePending = false;
            }
        }
        if (nonePending) {
            System.out.println("None");
        }

        System.out.println("\nRemaining Stock:\n");
        for (Map.Entry<String, Integer> entry : resource_Allocation.availableStock.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class StatusModule {
    MyHashTable areaStatus = new MyHashTable();

    public void initializeStatus(ArrayList<Area> areas) {
        for (Area a : areas) {
            areaStatus.put(a.name, "Pending");
        }
    }

    public void updateStatus(String name, String status) {
        areaStatus.put(name, status);
    }

    public void refreshStatuses(resource_Allocation.MyQueue dispatched, resource_Allocation.MyQueue pending) {
        resource_Allocation.Node td = dispatched != null ? dispatched.front : null;
        while (td != null) {
            updateStatus(td.data.name, "Delivered");
            td = td.next;
        }
        resource_Allocation.Node tp = pending != null ? pending.front : null;
        while (tp != null) {
            updateStatus(tp.data.name, "Pending");
            tp = tp.next;
        }
    }

    public void showAllStatus() {
        areaStatus.displayAll();
    }
}

class resource_Allocation {
    static class Node {
        Area data;
        Node next;

        Node(Area data) {
            this.data = data;
        }
    }

    static class MyQueue {
        Node front, rear;

        MyQueue() {
            front = rear = null;
        }

        void enqueue(Area data) {
            Node newNode = new Node(data);
            if (rear == null) {
                front = rear = newNode;
                return;
            }
            if (!contains(data.name)) {
                rear.next = newNode;
                rear = newNode;
            }
        }

        Area dequeue() {
            if (front == null)
                return null;
            Area temp = front.data;
            front = front.next;
            if (front == null)
                rear = null;
            return temp;
        }

        boolean isEmpty() {
            return front == null;
        }

        boolean contains(String areaName) {
            Node current = front;
            while (current != null) {
                if (current.data.name.equals(areaName))
                    return true;
                current = current.next;
            }
            return false;
        }
    }

    static Map<String, Integer> availableStock = new HashMap<>();
    static {
        availableStock.put("Medical Kits", 1000);
        availableStock.put("Food Packets", 150);
        availableStock.put("Blankets", 800);
        availableStock.put("Water Bottles", 400);
    }
    public static MyQueue persistentPendingQueue = new MyQueue();
    public static MyQueue persistentDispatchedQueue = new MyQueue();

    public static void allocateResources(ArrayList<Area> sortedAreas) {
        persistentPendingQueue = new MyQueue();
        persistentDispatchedQueue = new MyQueue();

        for (Area a : sortedAreas) {
            int available = availableStock.getOrDefault(a.resourceType, 0);
            if (available >= a.resourceQty) {
                availableStock.put(a.resourceType, available - a.resourceQty);
                if (!persistentDispatchedQueue.contains(a.name)) {
                    persistentDispatchedQueue.enqueue(a);
                    System.out.println("Dispatched to: " + a.name + " (" + a.resourceQty + " " + a.resourceType + ")");
                }
            } else {
                persistentPendingQueue.enqueue(a);
                System.out.println("\nPending: " + a.name + " (Required: " + a.resourceQty + " " + a.resourceType
                        + ", Available: " + available + ")");
            }
        }
    }

    public static void restockResources(Map<String, Integer> restockMap) {
        for (Map.Entry<String, Integer> entry : restockMap.entrySet()) {
            String resourceType = entry.getKey();
            int addedQty = entry.getValue();
            availableStock.put(resourceType, availableStock.getOrDefault(resourceType, 0) + addedQty);
            System.out.println("\nRestocked " + resourceType + ": +" + addedQty);
        }
        System.out.println("\nRechecking pending areas...");

        MyQueue newPending = new MyQueue();
        while (!persistentPendingQueue.isEmpty()) {
            Area a = persistentPendingQueue.dequeue();
            int avail = availableStock.getOrDefault(a.resourceType, 0);
            if (avail >= a.resourceQty) {
                availableStock.put(a.resourceType, avail - a.resourceQty);
                if (!persistentDispatchedQueue.contains(a.name)) {
                    persistentDispatchedQueue.enqueue(a);
                    System.out.println("\nDispatched after restock: " + a.name + " (" + a.resourceQty + " "
                            + a.resourceType + ")");
                }
            } else {
                newPending.enqueue(a);
                System.out.println(
                        "\nStill pending: " + a.name + " (Required: " + a.resourceQty + ", Available: " + avail + ")");
            }
        }
        persistentPendingQueue = newPending;
        if (persistentPendingQueue.isEmpty()) {
            System.out.println("\nAll pending requests have been fulfilled!");
        } else {
            System.out.println("\nSome requests are still pending after restock.");
        }
    }
}

class PriorityModule {
    class MaxHeap {
        private ArrayList<Area> heap = new ArrayList<>();

        private int prioritize(Area a, Area b) {
            if (a.urgency != b.urgency)
                return Integer.compare(a.urgency, b.urgency);
            return Integer.compare(a.distance, b.distance);
        }

        void insert(Area area) {
            heap.add(area);
            int i = heap.size() - 1;
            while (i > 0 && prioritize(heap.get(i), heap.get(parent(i))) < 0) {
                swap(i, parent(i));
                i = parent(i);
            }
        }

        Area extractHighestPriority() {
            if (heap.isEmpty())
                return null;
            Area top = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            heapify(0);
            return top;
        }

        boolean isEmpty() {
            return heap.isEmpty();
        }

        private void heapify(int i) {
            int left = left(i), right = right(i), highest = i;
            if (left < heap.size() && prioritize(heap.get(left), heap.get(highest)) < 0)
                highest = left;
            if (right < heap.size() && prioritize(heap.get(right), heap.get(highest)) < 0)
                highest = right;
            if (highest != i) {
                swap(i, highest);
                heapify(highest);
            }
        }

        private void swap(int i, int j) {
            Area t = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, t);
        }

        private int parent(int i) {
            return (i - 1) / 2;
        }

        private int left(int i) {
            return 2 * i + 1;
        }

        private int right(int i) {
            return 2 * i + 2;
        }
    }

    public ArrayList<Area> getPriorityOrder(ArrayList<Area> areas) {
        ArrayList<Area> ordered = new ArrayList<>();
        MaxHeap heap = new MaxHeap();
        for (Area a : areas)
            heap.insert(a);
        while (!heap.isEmpty())
            ordered.add(heap.extractHighestPriority());
        return ordered;
    }

    public void showPriorityList(ArrayList<Area> areas) {
        System.out.println("\n=== PRIORITY ORDER (Based on Urgency & Distance) ===\n");
        System.out.println("Area         Distance  Urgency  Need");
        System.out.println("------------------------------------------------");
        for (Area a : areas)
            a.display();
    }
}

public class DisasterResourceSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AreaInputModule inputModule = new AreaInputModule();
        PriorityModule priorityModule = new PriorityModule();
        StatusModule statusModule = new StatusModule();

        ArrayList<Area> areaList = new ArrayList<>();
        ArrayList<Area> priorityList = new ArrayList<>();
        Map<String, Integer> restockMap = new HashMap<>();

        System.out.println("\n===== DISASTER RESOURCE MANAGEMENT SYSTEM =====\n");

        int choice;
        do {
            System.out.println("\n1. Input Area Data");
            System.out.println("2. Sort & View Areas by Priority");
            System.out.println("3. Allocate Resources to Areas");
            System.out.println("4. Restock & Reallocate Resources");
            System.out.println("5. View Delivery Status Summary");
            System.out.println("6. Exit\n");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    // Optional: Reset stock on new input for fresh run
                    resource_Allocation.availableStock.put("Medical Kits", 1000);
                    resource_Allocation.availableStock.put("Food Packets", 150);
                    resource_Allocation.availableStock.put("Blankets", 800);
                    resource_Allocation.availableStock.put("Water Bottles", 400);

                    areaList = inputModule.getAreaData();
                    statusModule.initializeStatus(areaList);
                    break;
                case 2:
                    priorityList = priorityModule.getPriorityOrder(areaList);
                    priorityModule.showPriorityList(priorityList);
                    break;
                case 3:
                    if (priorityList.isEmpty()) {
                        resource_Allocation.allocateResources(areaList);
                    } else {
                        resource_Allocation.allocateResources(priorityList);
                    }
                    statusModule.refreshStatuses(resource_Allocation.persistentDispatchedQueue,
                            resource_Allocation.persistentPendingQueue);
                    break;
                case 4:
                    restockMap.clear();
                    System.out.println("\nEnter number of resource types you want to restock:");
                    int resCount = sc.nextInt();
                    sc.nextLine();
                    for (int i = 0; i < resCount; i++) {
                        System.out.print(
                                "\nEnter Resource Type (Medical Kits / Food Packets / Blankets / Water Bottles): ");
                        String resTypeInput = sc.nextLine().trim();
                        String normalizedResType = AreaInputModule.normalizeResourceType(resTypeInput);
                        if (normalizedResType == null) {
                            System.out.println("\nInvalid resource type entered! Try again");
                            i--;
                            continue;
                        }
                        System.out.print("\nEnter quantity to add for " + normalizedResType + ": ");
                        int qty = sc.nextInt();
                        sc.nextLine();
                        restockMap.put(normalizedResType, restockMap.getOrDefault(normalizedResType, 0) + qty);
                    }
                    resource_Allocation.restockResources(restockMap);
                    statusModule.refreshStatuses(resource_Allocation.persistentDispatchedQueue,
                            resource_Allocation.persistentPendingQueue);
                    break;
                case 5:
                    statusModule.showAllStatus();
                    break;
                case 6:
                    System.out.println("\nExiting system. Stay safe!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Try again.");
                    break;
            }
        } while (choice != 6);
        sc.close();
    }
}
