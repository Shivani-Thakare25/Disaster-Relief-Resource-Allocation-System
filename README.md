# Disaster Resource Management System

## Overview

The **Disaster Resource Management System** is a Java-based console application designed to efficiently manage and allocate resources during disaster situations.
It prioritizes affected areas based on urgency and distance, ensuring that critical regions receive help first.

## Features

* Input details of multiple affected areas
* Priority-based sorting using **Heap (Max Heap)**
* Smart resource allocation based on availability
* Pending queue for insufficient resources
* Restocking and automatic reallocation
* Delivery status tracking (Delivered / Pending)
* Remaining stock display

## How It Works

1. **Input Area Data**

   User enters details like area name, urgency, distance, and resource needs.

2. **Priority Calculation**

   Areas are sorted using:

   1.**Urgency (High → Low)**
   2. **Distance (Near → Far)**

3. **Resource Allocation**

   1. If stock is available → resources are dispatched
   2. If not → area is added to pending queue

4. **Restocking**
   1. Resources can be added later
   2. Pending areas are rechecked automatically

5. **Status Tracking**
Delivered and Pending areas are stored using a hash table

## Technologies Used

* **Java**
* **Data Structures:**

1. ArrayList
2. Heap (Priority Queue)
3. Queue (Linked List implementation)
4. HashMap
5. Custom Hash Table
