################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../descuento.c \
../holaMundo.c \
../jazztelDecrypter.c \
../permutaciones.c \
../rombo.c 

OBJS += \
./descuento.o \
./holaMundo.o \
./jazztelDecrypter.o \
./permutaciones.o \
./rombo.o 

C_DEPS += \
./descuento.d \
./holaMundo.d \
./jazztelDecrypter.d \
./permutaciones.d \
./rombo.d 


# Each subdirectory must supply rules for building sources it contributes
%.o: ../%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


