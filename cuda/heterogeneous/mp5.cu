// MP 5 Scan
// Given a list (lst) of length n
// Output its prefix sum = {lst[0], lst[0] + lst[1], lst[0] + lst[1] + ... + lst[n-1]}
// Due Tuesday, January 22, 2013 at 11:59 p.m. PST

#include    <wb.h>

#define BLOCK_SIZE 1024 //@@ You can change this

#define wbCheck(stmt) do {                                 \
        cudaError_t err = stmt;                            \
        if (err != cudaSuccess) {                          \
            wbLog(ERROR, "Failed to run stmt ", #stmt);    \
            return -1;                                     \
        }                                                  \
    } while(0)

__global__ void _scan(float *input, float *output, int len, float *blockSums, int numBlocks) {
	__shared__ float scan_array[2*BLOCK_SIZE];

	int stride, index,t=threadIdx.x,i=2*blockIdx.x*blockDim.x+t;
	scan_array[t]=(i<len ? input[i] : 0);
	scan_array[t+BLOCK_SIZE]=(i+BLOCK_SIZE<len ? input[i+BLOCK_SIZE] :0 );
	for(stride=1;stride<=BLOCK_SIZE;stride*=2) {
		__syncthreads();
		index=(t+1)*stride*2-1;
		if (index<2*BLOCK_SIZE) {
			scan_array[index]+=scan_array[index-stride];
		}
	}
	for(stride=BLOCK_SIZE/2;stride>0;stride/=2) {
		__syncthreads();
		index=(t+1)*stride*2-1;
		if (index+stride<2*BLOCK_SIZE) {
			scan_array[index+stride]+=scan_array[index];
		}
	}
	__syncthreads();
	if (blockSums!=0) {
		blockSums[blockIdx.x]=scan_array[2*BLOCK_SIZE-1];
	}
	if (i<len) {
		output[i]=scan_array[t];
	}
	if (i+BLOCK_SIZE<len) {
		output[i+BLOCK_SIZE]=scan_array[t+BLOCK_SIZE];
	}
}
__global__ void sum(float *input,float *blockSums,int len) {
	int i=2*blockIdx.x*blockDim.x+threadIdx.x;
	if (blockIdx.x>0 ) {
		if (i<len) {
			input[i]+=blockSums[blockIdx.x-1];
		}
		if (i+BLOCK_SIZE<len) {
			input[i+BLOCK_SIZE]+=blockSums[blockIdx.x-1];
		}
	}
}
void scan(float * input, float * output, int len) {
    //@@ Modify the body of this function to complete the functionality of
    //@@ the scan on the device
    //@@ You may need multiple kernel calls; write your kernels before this
    //@@ function and call them from here
	float *blockSumsInput, *blockSumsOutput;
	int numBlocks=((len-1)/(2*BLOCK_SIZE)+1);
    dim3 DimGrid(numBlocks, 1, 1);
    dim3 DimBlock(BLOCK_SIZE, 1, 1);
	cudaMalloc((void**)&blockSumsInput,numBlocks*sizeof(float));
	cudaMalloc((void**)&blockSumsOutput,numBlocks*sizeof(float));
	_scan<<<DimGrid,DimBlock>>>(input,output, len,blockSumsInput,numBlocks);
	dim3 DimGridB((numBlocks-1)/(2*BLOCK_SIZE)+1,1,1);
	dim3 DimBlockB(BLOCK_SIZE,1,1);
	_scan<<<DimGridB,DimBlockB>>>(blockSumsInput,blockSumsOutput,numBlocks,0,0);
	if (numBlocks>1) {
		sum<<<DimGrid,DimBlock>>>(output,blockSumsOutput,len);
	}

}

int main(int argc, char ** argv) {
    wbArg_t args;
    float * hostInput; // The input 1D list
    float * hostOutput; // The output list
    float * deviceInput;
    float * deviceOutput;
    int numElements; // number of elements in the list

    args = wbArg_read(argc, argv);

    wbTime_start(Generic, "Importing data and creating memory on host");
    hostInput = (float *) wbImport(wbArg_getInputFile(args, 0), &numElements);
    hostOutput = (float*) malloc(numElements * sizeof(float));
    wbTime_stop(Generic, "Importing data and creating memory on host");

    wbLog(TRACE, "The number of input elements in the input is ", numElements);
    wbLog(TRACE, "hostInput[0]: ", hostInput[0]);
    wbTime_start(GPU, "Allocating GPU memory.");
    wbCheck(cudaMalloc((void**)&deviceInput, numElements*sizeof(float)));
    wbCheck(cudaMalloc((void**)&deviceOutput, numElements*sizeof(float)));
    wbTime_stop(GPU, "Allocating GPU memory.");

    wbTime_start(GPU, "Clearing output memory.");
    wbCheck(cudaMemset(deviceOutput, 0, numElements*sizeof(float)));
    wbTime_stop(GPU, "Clearing output memory.");

    wbTime_start(GPU, "Copying input memory to the GPU.");
    wbCheck(cudaMemcpy(deviceInput, hostInput, numElements*sizeof(float), cudaMemcpyHostToDevice));
    wbTime_stop(GPU, "Copying input memory to the GPU.");

    //@@ Initialize the grid and block dimensions here

    wbTime_start(Compute, "Performing CUDA computation");
    //@@ Modify this to complete the functionality of the scan
    //@@ on the deivce
    scan(deviceInput,deviceOutput,numElements);
    cudaDeviceSynchronize();
    wbTime_stop(Compute, "Performing CUDA computation");

    wbTime_start(Copy, "Copying output memory to the CPU");
    wbCheck(cudaMemcpy(hostOutput, deviceOutput, numElements*sizeof(float), cudaMemcpyDeviceToHost));
    wbLog(TRACE, "hostOutput[0]: ", hostOutput[0]);
    wbTime_stop(Copy, "Copying output memory to the CPU");

    wbTime_start(GPU, "Freeing GPU Memory");
    cudaFree(deviceInput);
    cudaFree(deviceOutput);
    wbTime_stop(GPU, "Freeing GPU Memory");

    wbSolution(args, hostOutput, numElements);

    free(hostInput);
    free(hostOutput);

    return 0;
}

