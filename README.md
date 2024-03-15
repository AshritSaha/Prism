# VITC_23SE15_Network_Monitor_Utility

SRIB-PRISM Program

## Problem

The current approach for reading RF network parameters during modem feature testing is inefficient. Third-party apps lack the required functionality. A utility is needed to fetch live RF parameters for automation, monitoring changes in network stability.

## Solution

- Android app to continuously read network RF parameters for LTE, NSA & SA networks.
- Monitor changes in real-time, including call drops, data stalls, SCG failures, handovers, PCI/RAT changes, band changes, and bandwidth changes.
- Expose CLI/APIs for data retrieval.
- Use Telephony APIs/Google APIs to read network parameters.
- Desktop IS server with MongoDB for data storage.
- Track call status and call drop status with location details.

## Milestones

**Kick Off (1st Month):**

- Set up project requirements.
- Implement reading of RF network parameters.
- Develop Android app utility.

**Milestone 1 (2nd Month):**

- Develop Desktop IS server with MongoDB.
- Monitor and handle network change notifications.
- Add identification features for SCG failures, CA status, handovers, PCI/RAT changes, band changes, and bandwidth changes.

**Closure (3rd Month):**

- Develop call drop and data stall identification features.
- Implement APIs to fetch information and RF parameters on available SIM cards.

## Worklet Details

- **Name:** Network Monitor Utility
- **Group:** Visual Intelligence & Communications Quality Group
- **Mentor 1:** Nandeesha Kumar
- **Mentor 2:** Sai Dileep Kumar Gorle
- **Mentor 3:** Venkatraman Bakthavachalu
- **Prof 1 Name:** Dr. Thirupurasundari
- **Prof 2 Name:** Dr. Richards Joe Stanislaus
- **Student 1 Name:** Shubham Singh
- **Student 2 Name:** Sourav Patel
- **Student 3 Name:** Ashrit Saha
- **Student 4 Name:** Navin Kumar L
- **Student 5 Name:** Utkarsh Pandey
