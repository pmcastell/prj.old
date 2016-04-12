#include    <wb.h>

// Check ec2-174-129-21-232.compute-1.amazonaws.com:8080/mp/6 for more information

#define wbCheck(stmt) do {                                 \
        cudaError_t err = stmt;                            \
        if (err != cudaSuccess) {                          \
            wbLog(ERROR, "Failed to run stmt ", #stmt);    \
            return -1;                                     \
        }                                                  \
    } while(0)

#define MASK_WIDTH  5
#define Mask_radius MASK_WIDTH/2
#define CHANNELS 3
#define TILE_SIZE 5

//@@ INSERT CODE HERE
//def clamp(x, start, end)
//      return min(max(x, start), end)
//    end
//#define clamp(x,s,e) ((x>s?x:s)<e?(x>s?x:s):e)
#define MIN(x,y) (x<y?x:y)
#define MAX(x,y) (x>y?x:y)
#define clamp(x,s,e) MIN(MAX(x,s),e)
#define _N(y,x,k) N[(y)*totalWidth+(x)*CHANNELS+k]
#define _P(y,x,k) P[(y)*totalWidth+(x)*CHANNELS+k]

//__device__ float clamp(float x, float s, float e) {
//	float max=(x>s?x:s);
//	return (max<e?max:e);
//}
//__global__ void convolution2DImage(float *N, const float __restrict__ *M, float *P, int Width, int Height) {
__global__ void convolution2DImage(float *N, const float *M, float *P, int Width, int Height) {
	__shared__ float N_ds[TILE_SIZE+MASK_WIDTH-1][TILE_SIZE+MASK_WIDTH-1][CHANNELS];
	int tx=threadIdx.x, ty=threadIdx.y, bx=blockIdx.x, by=blockIdx.y, totalWidth=CHANNELS*Width;

	int indx=bx*blockDim.x+tx,
		indy=by*blockDim.y+ty;
	//++++++++++ QUITAR
	int f=2,c=2;
	if (indy==f && indx==c) {
		printf("indy: %d, indx: %d\n",indy,indx);
		for(int i=f-Mask_radius;i<=f+2*Mask_radius;i++) {
			for(int j=c-Mask_radius;j<=c+2*Mask_radius;j++) {
				//printf("Indice : %d",(i)*totalWidth+(j)*CHANNELS);
				printf("N[%d][%d]=%f.",i,j,N[(i)*totalWidth+(j)*CHANNELS]);
			}
			printf("\n");
		}
	}
	__syncthreads();
	//++++++++++ QUITAR
	//++++++++++ QUITAR
		__syncthreads();
		int f=0,c=192;
		if (indy==f && indx==c) {
			printf("indy: %d, indx: %d\n",indy,indx);
			for(int i=0;i<20;i++) {
				for(int j=190;j<200;j++) {
					//printf("Indice : %d",(i)*totalWidth+(j)*CHANNELS);
					printf("%f;",_N(i,j,0));
				}
				printf("\n");
			}
		}
		__syncthreads();
	//++++++++++ QUITAR
	if (indx<Width && indy<Height) {
		int haloIndX,haloIndY;
		if (tx>=TILE_SIZE-Mask_radius) { // left
			haloIndX=tx-(TILE_SIZE-Mask_radius);
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty+Mask_radius][haloIndX][k]=(indx-TILE_SIZE<0 ? 0 : _N(indy,indx-TILE_SIZE,k));
			}
		}
		if (ty>=TILE_SIZE-Mask_radius) { // up
			haloIndY=ty-(TILE_SIZE-Mask_radius);
			for(int k=0;k<CHANNELS;k++) {
				N_ds[haloIndY][tx+Mask_radius][k]=(indy-TILE_SIZE<0 ? 0 : _N(indy-TILE_SIZE,indx,k));
			}
		}
		if (tx<Mask_radius) { // right
			haloIndX=Mask_radius+TILE_SIZE+tx;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty+Mask_radius][haloIndX][k]=(indx+TILE_SIZE>=Width ? 0 : _N(indy,indx+TILE_SIZE,k));
			}
		}
		if (ty<Mask_radius) { // bottom
			haloIndY=Mask_radius+TILE_SIZE+ty;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[haloIndY][tx+Mask_radius][k]=(indy+TILE_SIZE>=Height ? 0 : _N(indy+TILE_SIZE,indx,k));
			}
		}
		if (tx<Mask_radius && ty<Mask_radius) { // upper left corner
			for(int k=0;k<CHANNELS;k++) {
				if (indy-Mask_radius<0 || indx-Mask_radius<0) {
					N_ds[ty][tx][k]=0;
				} else {
					N_ds[ty][tx][k]=_N(indy-Mask_radius,indx-Mask_radius,k);
				}
			}
		}
		if (tx<Mask_radius && ty>=TILE_SIZE-Mask_radius) { // bottom left corner
			for(int k=0;k<CHANNELS;k++) {
				if (indy+Mask_radius>=Height || indx-Mask_radius<0) {
					N_ds[Mask_radius+ty+Mask_radius][tx][k]=0;
				} else {
					N_ds[Mask_radius+ty+Mask_radius][tx][k]=_N(indy+Mask_radius,indx-Mask_radius,k);
				}
			}
		}
		if (tx>=TILE_SIZE-Mask_radius && ty>=TILE_SIZE-Mask_radius) { //bottom right corner
			for(int k=0;k<CHANNELS;k++) {
				if (indy+Mask_radius>=Height || indx+Mask_radius>=Width) {
					N_ds[Mask_radius+ty+Mask_radius][Mask_radius+tx+Mask_radius][k]=0;
				} else {
					N_ds[Mask_radius+ty+Mask_radius][Mask_radius+tx+Mask_radius][k]=_N(indy+Mask_radius,indx+Mask_radius,k);
				}
			}
		}
		if (tx>=TILE_SIZE-Mask_radius && ty<Mask_radius) { // upper right corner
			for(int k=0;k<CHANNELS;k++) {
				if (indy-Mask_radius<0 || indx+Mask_radius>=Width) {
					N_ds[ty][Mask_radius+tx+Mask_radius][k]=0;
				} else {
					N_ds[ty][Mask_radius+tx+Mask_radius][k]=_N(indy-Mask_radius,indx+Mask_radius,k);
				}
			}
		}
		for(int k=0;k<CHANNELS;k++) {
			N_ds[ty+Mask_radius][tx+Mask_radius][k]=_N(indy,indx,k);
		}
		__syncthreads();
		//++++++++++++++++++ QUITAR
		if (indy==f && indx==c) {
			printf("\n");
			printf("indy: %d, indx: %d\n",indy,indx);
			printf("Rangos i: %d--%d, j: %d-%d.\n",f-Mask_radius,f+2*Mask_radius+TILE_SIZE/2,c-Mask_radius,c+2*Mask_radius+TILE_SIZE/2);
			for(int i=f-Mask_radius;i<=f+2*Mask_radius+TILE_SIZE/2;i++) {
				for(int j=c-Mask_radius;j<=c+2*Mask_radius+TILE_SIZE/2;j++) {
					//printf("Indice : %d",(i)*totalWidth+(j)*CHANNELS);
					printf("N_ds[%d][%d]=%f.",i,j,N_ds[i][j][0]);
				}
				printf("\n");
			}
		}
		//++++++++++++++++++ QUITAR
		//++++++++++++++++++ QUITAR
					if (indy==f && indx==c) {
						printf("\n");
						printf("indy: %d, indx: %d\n",indy,indx);
						printf("Rangos i: %d--%d, j: %d-%d.\n",f-Mask_radius,f+2*Mask_radius+TILE_SIZE/2,c-Mask_radius,c+2*Mask_radius+TILE_SIZE/2);
						for(int i=0;i<20;i++) {
							for(int j=0;j<20;j++) {
								//printf("Indice : %d",(i)*totalWidth+(j)*CHANNELS);
								printf("%f;",N_ds[i][j][0]);
							}
							printf("\n");
						}
					}
					__syncthreads();
			//++++++++++++++++++ QUITAR
		float PValue[CHANNELS];
		for(int k=0;k<CHANNELS;k++) {
			PValue[k]=0;
			for(int i=0;i<MASK_WIDTH;i++) {
				for(int j=0;j<MASK_WIDTH;j++) {
					PValue[k]+=N_ds[ty+i][tx+j][k]*M[i*MASK_WIDTH+j];
				}
			}
			//P[indy*totalWidth+indx*CHANNELS+k]=clamp(PValue[k],0,1);
			_P(indy,indx,k)=clamp(PValue[k],0,1);
		}
	}
}


int main(int argc, char* argv[]) {
    wbArg_t arg;
    int maskRows;
    int maskColumns;
    int imageChannels;
    int imageWidth;
    int imageHeight;
    char * inputImageFile;
    char * inputMaskFile;
    wbImage_t inputImage;
    wbImage_t outputImage;
    float * hostInputImageData;
    float * hostOutputImageData;
    float * hostMaskData;
    float * deviceInputImageData;
    float * deviceOutputImageData;
    float * deviceMaskData;

    arg = wbArg_read(argc, argv); /* parse the input arguments */

    inputImageFile = wbArg_getInputFile(arg, 0);
    inputMaskFile = wbArg_getInputFile(arg, 1);

    inputImage = wbImport(inputImageFile);
    hostMaskData = (float *) wbImport(inputMaskFile, &maskRows, &maskColumns);

    assert(maskRows == 5); /* mask height is fixed to 5 in this mp */
    assert(maskColumns == 5); /* mask width is fixed to 5 in this mp */

    imageWidth = wbImage_getWidth(inputImage);
    imageHeight = wbImage_getHeight(inputImage);
    imageChannels = wbImage_getChannels(inputImage);

    outputImage = wbImage_new(imageWidth, imageHeight, imageChannels);

    hostInputImageData = wbImage_getData(inputImage);
    hostOutputImageData = wbImage_getData(outputImage);

    wbTime_start(GPU, "Doing GPU Computation (memory + compute)");

    wbTime_start(GPU, "Doing GPU memory allocation");
    cudaMalloc((void **) &deviceInputImageData, imageWidth * imageHeight * imageChannels * sizeof(float));
    cudaMalloc((void **) &deviceOutputImageData, imageWidth * imageHeight * imageChannels * sizeof(float));
    cudaMalloc((void **) &deviceMaskData, maskRows * maskColumns * sizeof(float));
    wbTime_stop(GPU, "Doing GPU memory allocation");


    wbTime_start(Copy, "Copying data to the GPU");
    cudaMemcpy(deviceInputImageData,
               hostInputImageData,
               imageWidth * imageHeight * imageChannels * sizeof(float),
               cudaMemcpyHostToDevice);
    cudaMemcpy(deviceMaskData,
               hostMaskData,
               maskRows * maskColumns * sizeof(float),
               cudaMemcpyHostToDevice);
    wbTime_stop(Copy, "Copying data to the GPU");

    wbTime_start(Compute, "Doing the computation on the GPU");
    //@@ INSERT CODE HERE
    dim3 DimGrid((imageWidth-1)/TILE_SIZE + 1, ( imageHeight-1)/TILE_SIZE+1, 1);
    dim3 DimBlock(TILE_SIZE, TILE_SIZE, 1);
    printf("lanzando el kernel: \n");
    printf("tam img: %d,%d, channels: %d\n",imageWidth,imageHeight,imageChannels);
    convolution2DImage<<<DimGrid,DimBlock>>>(deviceInputImageData,deviceMaskData,deviceOutputImageData,imageWidth,imageHeight);
    wbTime_stop(Compute, "Doing the computation on the GPU");


    wbTime_start(Copy, "Copying data from the GPU");
    cudaMemcpy(hostOutputImageData,
               deviceOutputImageData,
               imageWidth * imageHeight * imageChannels * sizeof(float),
               cudaMemcpyDeviceToHost);
    wbTime_stop(Copy, "Copying data from the GPU");

    wbTime_stop(GPU, "Doing GPU Computation (memory + compute)");

    wbSolution(arg, outputImage);

    cudaFree(deviceInputImageData);
    cudaFree(deviceOutputImageData);
    cudaFree(deviceMaskData);

    free(hostMaskData);
    wbImage_delete(outputImage);
    wbImage_delete(inputImage);

    return 0;
}
