# User Stories for Online Banking System

## User Story 1: Customer Registration
As a new customer, I want to register for an online banking account so that I can access banking services.

### Scenario:
- **Given** I am not registered to the online banking service
- **When** I navigate to the bank's registration page
- **And** submit my firstname, lastname and birthdate
- **Then** I receive confirmation of successful registration in form of a username and password

## User Story 2: Customer Login
As a registered customer, I want to be able to log into my online banking account.

### Scenario:
- **Given** I am registered to the online banking service
- **When** I navigate to the bank's login page
- **And** submit a valid combination of username and password
- **Then** I am logged in
- **And** being redirected to my homepage

## User Story 3: Account Balance Check
As a customer, I want to check my account balance online to monitor my finances.

### Scenario:
- **Given** I am logged into my online banking account
- **When** I am visiting my homepage
- **Then** I should see a list of all my accounts and their balance

## User Story 4: Add account
As a customer, I want to be able to add a new account, so I can have accounts for multiple different purposes.

### Scenario:
- **Given** I am logged into my online banking account
- **When** I am visiting my homepage
- **And** open a new account
- **Then** A new account with 0 balance will be related to my login

## TODO: User Story 5: Delete account
As a customer, I want to be able to delete any of my accounts, so I can keep a purposeful portfolio.

### Scenario:
- **Given** I am logged into my online banking account
- **When** I am visiting my homepage
- **And** decide to delete one of my accounts
- **And** have to specify the account which the current deposit will be transferred to
- **Then** that account will be deleted
- **And** the funds should be transferred successfully

## User Story 6: Fund Transfer
As a customer, I want to transfer funds between my accounts or to another account online.

### Scenario:
- **Given** I am logged into my online banking account
- **When** I choose to transfer money
- **And** submit the recipient IBAN and amount of swiss francs
- **Then** the funds should be transferred successfully
- **And** I receive confirmation of the transaction

## User Story 7: Online Banking Account Transaction History
As a customer, I want to view my transaction history for tracking purposes.

### Scenario:
- **Given** I am logged into my online banking account
- **When** I navigate to the transaction history page
- **Then** I should see a list of all my transactions

## User Story 8: Transaction History
As a bank advisor, I want to view a global transaction history for tracking purposes.

### Scenario:
- **Given** I am logged into my advisor account
- **When** I navigate to the transaction history page
- **Then** I should be presented a list of all transactions

## TODO: User Story 9: Account Overview
As a bank advisor, I want to have a list of all available accounts, so that I can help customers.

### Scenario:
- **Given** I am logged into my advisor account
- **When** I navigate to the account overview page
- **Then** I should be presented a list of all customer accounts

## TODO User Story 10: Change Password
As a customer, I want to change my online banking password for security reasons.

### Scenario:
- **Given** I am logged into my online banking account
- **When** I navigate to the settings page
- **And** submit my current password, new password and confirmation of the new password
- **Then** my password should be updated successfully

## User Story 11: Customer Support
As a customer, I want to have the possibility to contact the online banking account, so that I receive help when I need it.

### Scenario:
- **Given** I am on the homepage
- **When** I navigate to the support page
- **Then** I am presented a list of contact E-Mails and Phone-numbers
