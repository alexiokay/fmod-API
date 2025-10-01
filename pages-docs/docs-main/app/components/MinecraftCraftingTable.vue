<template>
  <div class="crafting-table-container w-full h-96 relative rounded-lg overflow-hidden bg-gradient-to-b from-amber-900 to-amber-800">
    <ClientOnly>
      <TresCanvas
        v-bind="gl"
        class="w-full h-full"
      >
        <!-- Scene Setup -->
        <TresPerspectiveCamera :position="[15, 9, 15]" :look-at="[0, 0, 0]" />

        <!-- Simple Minecraft-style sky sphere -->
        <TresMesh>
          <TresSphereGeometry :args="[100, 32, 32]" />
          <TresMeshBasicMaterial
            :color="isDark ? 0x0a0a1a : 0x87ceeb"
            :side="1"
          />
        </TresMesh>

        <!-- Twinkling stars for night sky -->
        <template v-if="isDark">
          <TresMesh
            v-for="(star, index) in stars"
            :key="index"
            :position="star.position"
          >
            <TresSphereGeometry :args="[star.size, 8, 8]" />
            <TresMeshBasicMaterial
              :color="0xffffff"
              :transparent="true"
              :opacity="star.currentOpacity"
            />
          </TresMesh>
        </template>

        <!-- Static Sun Light -->
        <TresDirectionalLight
          :position="[50, 80, 30]"
          :intensity="isDark ? 0 : 1.5"
          :cast-shadow="true"
          :color="isDark ? 0x4a5568 : 0xffeaa7"
        />

        <!-- Moonlight for night -->
        <TresDirectionalLight
          v-if="isDark"
          :position="[-80, 100, -40]"
          :intensity="0.8"
          :cast-shadow="true"
          :color="0xb3d9ff"
        />

        <!-- Reduced ambient light since HDR provides environmental lighting -->
        <TresAmbientLight
          :intensity="isDark ? 0.2 : 0.3"
          :color="isDark ? 0x1a1a2e : 0x87ceeb"
        />

        <!-- Atmospheric hemisphere -->
        <TresHemisphereLight
          :sky-color="isDark ? 0x0a0a1a : 0x87ceeb"
          :ground-color="isDark ? 0x1a1a1a : 0x8b6914"
          :intensity="isDark ? 0.3 : 0.4"
        />

        <!-- Minecraft-style layered terrain with grass, dirt, and stone -->
        <TresGroup :position="[0, 0, 0]">
          <!-- Grass blocks with multi-material -->
          <template v-for="(block, index) in floorBlocks" :key="index">
            <TresMesh
              v-if="block.texture === 'grass'"
              :position="block.position"
              :scale="[block.scale, block.scale, block.scale]"
              :receive-shadow="true"
              :cast-shadow="true"
              :material="grassBlockMaterials"
            >
              <TresBoxGeometry :args="[1, 1, 1]" />
            </TresMesh>

            <!-- Dirt and stone blocks with single material -->
            <TresMesh
              v-else
              :position="block.position"
              :scale="[block.scale, block.scale, block.scale]"
              :receive-shadow="true"
              :cast-shadow="true"
            >
              <TresBoxGeometry :args="[1, 1, 1]" />
              <TresMeshStandardMaterial
                :map="block.texture === 'dirt' ? dirtTexture : stoneTexture"
                :roughness="0.9"
                :metalness="0.0"
              />
            </TresMesh>
          </template>
        </TresGroup>


        <!-- Crafting Table Model - Static, no rotation, closer to camera -->
        <TresGroup ref="craftingTableRef" :position="[3, 1, 7]" :scale="[2, 2, 2]">
          <Suspense>
            <primitive
              v-if="modelScene"
              :object="modelScene"
            />
            <template #fallback>
              <!-- Fallback simple table while loading -->
              <TresMesh :cast-shadow="true" :receive-shadow="true">
                <TresBoxGeometry :args="[2, 0.25, 2]" />
                <TresMeshLambertMaterial :color="0x8B4513" />
              </TresMesh>
              <!-- Simple legs -->
              <TresMesh
                v-for="(pos, index) in legPositions"
                :key="index"
                :position="[pos[0]/2, pos[1]/2, pos[2]/2]"
                :cast-shadow="true"
              >
                <TresBoxGeometry :args="[0.15, 0.75, 0.15]" />
                <TresMeshLambertMaterial :color="0x8B4513" />
              </TresMesh>
            </template>
          </Suspense>
        </TresGroup>

        <!-- Background Xurkitree Model -->
        <TresGroup ref="xurkitreeRef" :position="[0, 0, -20]" :scale="[2, 2, 2]" :rotation="xurkitreeRotation">
          <Suspense>
            <primitive
              v-if="xurkitreeScene"
              :object="xurkitreeScene"
            />
            <template #fallback>
              <!-- Fallback to show loading state -->
              <TresMesh>
                <TresBoxGeometry :args="[2, 6, 2]" />
                <TresMeshStandardMaterial :color="0x00ff00" />
              </TresMesh>
            </template>
          </Suspense>
        </TresGroup>

        <!-- Minecraft Creeper Model -->
        <TresGroup :position="[-9, -3, 8]" :scale="[0.3, 0.3, 0.3]" :rotation="[0, Math.PI / -2, 0]">
          <Suspense>
            <primitive
              v-if="creeperScene"
              :object="creeperScene"
            />
          </Suspense>
        </TresGroup>

        <!-- G-Man Toilet Model -->
        <TresGroup :position="[4, -2, -8]" :scale="[5.5, 5.5, 5.5]">
          <Suspense>
            <primitive
              v-if="gmanToiletScene"
              :object="gmanToiletScene"
            />
          </Suspense>
        </TresGroup>



      </TresCanvas>
    </ClientOnly>


    <!-- Status indicator -->
    <div v-if="!modelScene" class="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center text-amber-200 pointer-events-none">
      <div class="text-4xl mb-4 animate-bounce">🔨</div>
      <div class="text-lg pixelated-text" style="font-family: 'Minecraft', 'Courier New', monospace;">
        {{ isLoading ? 'Loading 3D Crafting Table...' : (error ? 'Model Load Failed' : 'Using Fallback Table') }}
      </div>
      <div v-if="isLoading" class="text-sm text-amber-300 mt-2">
        Please wait, loading 14MB model...
      </div>
      <div v-if="error" class="text-xs text-red-300 mt-2">{{ error }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed, watchEffect } from 'vue'
import { useGraph, useLoader } from '@tresjs/core'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader.js'
import { DRACOLoader } from 'three/examples/jsm/loaders/DRACOLoader.js'
import * as THREE from 'three'

// Use Nuxt color mode for day/night
const { $colorMode } = useNuxtApp()
const isDark = computed(() => $colorMode.value === 'dark')

// Setup DRACO loader for compressed GLTFs
const dracoLoader = new DRACOLoader()
dracoLoader.setDecoderPath('https://www.gstatic.com/draco/versioned/decoders/1.5.6/')

console.log('Attempting to load crafting table from: /models/minecraft_crafting_table.glb')
console.log('Attempting to load Xurkitree from: /models/xurkitree_bedrock_model.glb')

// Load the crafting table model using useLoader with proper DRACO setup
const { state: model, isLoading, error } = useLoader(
  GLTFLoader,
  '/models/minecraft_crafting_table.glb',
  {
    extensions: (loader) => {
      if (loader instanceof GLTFLoader) {
        loader.setDRACOLoader(dracoLoader)
      }
    },
  },
)

// Load the Xurkitree background model
const { state: xurkitreeModel } = useLoader(
  GLTFLoader,
  '/models/xurkitree_bedrock_model.glb',
  {
    extensions: (loader) => {
      if (loader instanceof GLTFLoader) {
        loader.setDRACOLoader(dracoLoader)
      }
    },
  },
)

// Load Minecraft Creeper model
const { state: creeperModel } = useLoader(
  GLTFLoader,
  '/models/minecraft_creeper.glb',
  {
    extensions: (loader) => {
      if (loader instanceof GLTFLoader) {
        loader.setDRACOLoader(dracoLoader)
      }
    },
  },
)

// Load G-Man Toilet model
const { state: gmanToiletModel } = useLoader(
  GLTFLoader,
  '/models/gman_toilet.glb',
  {
    extensions: (loader) => {
      if (loader instanceof GLTFLoader) {
        loader.setDRACOLoader(dracoLoader)
      }
    },
  },
)

// Load Bluetooth Speaker model
const { state: bluetoothSpeakerModel } = useLoader(
  GLTFLoader,
  '/models/bluetooth_speaker.glb',
  {
    extensions: (loader) => {
      if (loader instanceof GLTFLoader) {
        loader.setDRACOLoader(dracoLoader)
      }
    },
  },
)

// Load Minecraft textures for floor
const textureLoader = new THREE.TextureLoader()
const dirtTexture = textureLoader.load('/textures/dirt.png')
const stoneTexture = textureLoader.load('/textures/stone.png')
const grassSideTexture = textureLoader.load('/textures/grass_block_side.png')
const grassTopTexture = textureLoader.load('/textures/grass_block_top.png')

// Apply nearest neighbor filtering for pixelated Minecraft look
for (let tex of [dirtTexture, stoneTexture, grassSideTexture, grassTopTexture]) {
  tex.magFilter = THREE.NearestFilter
  tex.minFilter = THREE.NearestFilter
  tex.wrapS = THREE.RepeatWrapping
  tex.wrapT = THREE.RepeatWrapping
}

// Create proper Minecraft grass block materials (different textures per face)
// Cube faces order: [right, left, top, bottom, front, back]
const grassBlockMaterials = [
  new THREE.MeshStandardMaterial({ map: grassSideTexture, roughness: 0.9, metalness: 0.0 }), // right
  new THREE.MeshStandardMaterial({ map: grassSideTexture, roughness: 0.9, metalness: 0.0 }), // left
  new THREE.MeshStandardMaterial({ map: grassTopTexture, roughness: 0.9, metalness: 0.0 }),   // top (green grass)
  new THREE.MeshStandardMaterial({ map: dirtTexture, roughness: 0.9, metalness: 0.0 }),      // bottom (dirt)
  new THREE.MeshStandardMaterial({ map: grassSideTexture, roughness: 0.9, metalness: 0.0 }), // front
  new THREE.MeshStandardMaterial({ map: grassSideTexture, roughness: 0.9, metalness: 0.0 })  // back
]

// Generate realistic Minecraft-style terrain - flat center with mountains at horizon
const floorBlocks = []
const radius = 20

for (let x = -radius; x <= radius; x++) {
  for (let z = -radius; z <= radius; z++) {
    const distanceFromCenter = Math.sqrt(x * x + z * z)

    if (distanceFromCenter < radius) {
      // Flat center, mountains at edges
      let surfaceHeight

      if (distanceFromCenter < 8) {
        // Lower flat center area (clearing)
        surfaceHeight = -2
      } else {
        // Mountains/hills at the edges
        const heightFactor = (distanceFromCenter - 8) / (radius - 8)
        const noise = Math.sin(x * 0.3) * Math.cos(z * 0.3)
        surfaceHeight = Math.floor(heightFactor * 4 + noise * 2) // Gets higher towards edges
      }

      // Skip some edge blocks
      const edgeFactor = distanceFromCenter / radius
      const shouldSkip = edgeFactor > 0.9 && Math.random() > 0.3

      if (!shouldSkip) {
        // Bottom stone layers (always visible at least 1-2 layers)
        const bottomHeight = -2

        for (let y = bottomHeight; y <= surfaceHeight; y++) {
          // Determine block type based on position
          const isTopLayer = y === surfaceHeight
          const isDirtLayer = y === surfaceHeight - 1

          // At edges, randomly expose stone
          const shouldExposeStone = distanceFromCenter > 10 && isTopLayer && Math.random() > 0.7

          let texture = 'stone' // Default to stone

          if (isTopLayer && !shouldExposeStone) {
            texture = 'grass' // Top layer is grass
          } else if (isDirtLayer) {
            texture = 'dirt' // Layer below grass is dirt
          }

          floorBlocks.push({
            position: [x * 2, y * 2, z * 2],
            texture: texture,
            scale: 2
          })
        }
      }
    }
  }
}

// Extract the scenes and graphs
const modelScene = computed(() => model.value?.scene)
const xurkitreeScene = computed(() => xurkitreeModel.value?.scene)
const creeperScene = computed(() => creeperModel.value?.scene)
const gmanToiletScene = computed(() => gmanToiletModel.value?.scene)
const bluetoothSpeakerScene = computed(() => bluetoothSpeakerModel.value?.scene)
const graph = useGraph(modelScene)
const nodes = computed(() => graph.value?.nodes || {})

// Animation mixer for Xurkitree
let xurkitreeAnimationMixer = null
let xurkitreeActions = []

// Apply Minecraft-style textures and shadows when model loads
watchEffect(() => {
  if (modelScene.value) {
    console.log('Crafting table loaded successfully:', modelScene.value)
    console.log('Available nodes:', nodes.value)
    console.log('Node names:', Object.keys(nodes.value))

    // Get bounding box and center the model
    const box = new THREE.Box3().setFromObject(modelScene.value)
    const size = box.getSize(new THREE.Vector3())
    const center = box.getCenter(new THREE.Vector3())
    console.log('Model bounding box size:', size)
    console.log('Model center:', center)

    // Center the model at origin
    modelScene.value.position.sub(center)

    // Just set up shadows and let the model show its natural textures
    modelScene.value.traverse((child) => {
      if (child.isMesh) {
        child.castShadow = true
        child.receiveShadow = true
        child.visible = true

        // Log what we have
        console.log('Mesh:', child.name, 'Material:', child.material)

        if (child.material) {
          // Don't override - just ensure it's visible and properly lit
          child.material.needsUpdate = true
        }
      }
    })
  }
})

// Setup Xurkitree animations when model loads
watchEffect(() => {
  if (xurkitreeModel.value && xurkitreeScene.value) {
    console.log('Setting up Xurkitree animations...')
    console.log('Xurkitree model:', xurkitreeModel.value)
    console.log('Available animations:', xurkitreeModel.value.animations)

    // Create animation mixer
    xurkitreeAnimationMixer = new THREE.AnimationMixer(xurkitreeScene.value)

    // Load all animations
    if (xurkitreeModel.value.animations && xurkitreeModel.value.animations.length > 0) {
      xurkitreeActions = []

      // Play only the first animation to avoid conflicts
      const clip = xurkitreeModel.value.animations[0]
      if (clip) {
        console.log(`Loading single animation:`, clip.name, 'Duration:', clip.duration)
        const action = xurkitreeAnimationMixer.clipAction(clip)

        // Set smooth looping
        action.setLoop(THREE.LoopRepeat)
        action.clampWhenFinished = false
        action.reset()

        // Enable smooth blending
        action.setEffectiveWeight(1)
        action.setEffectiveTimeScale(1)

        action.play()
        xurkitreeActions.push(action)

        console.log(`Animation ${clip.name} - Weight:`, action.getEffectiveWeight())
      }

      console.log('Started', xurkitreeActions.length, 'Xurkitree animations')
    } else {
      console.log('No animations found in Xurkitree model')
    }
  }
})

// Tres.js renderer config
const gl = {
  clearColor: 0x1a1a1a,
  alpha: true,
  antialias: true
}

// Component refs
const craftingTableRef = ref()
const xurkitreeRef = ref()

// Xurkitree animation
const xurkitreeRotation = ref([0, 0, 0])

// Generate more random twinkling stars for night sky
const stars = reactive(Array.from({ length: 200 }, () => {
  // Generate stars in a sphere around the scene
  const theta = Math.random() * Math.PI * 2 // Random angle around Y axis
  const phi = Math.random() * Math.PI / 2.5 + Math.PI / 8 // Wider coverage of sky
  const distance = 75 + Math.random() * 20 // Distance from center

  const baseOpacity = 0.4 + Math.random() * 0.6

  return {
    position: [
      Math.sin(phi) * Math.cos(theta) * distance,
      Math.cos(phi) * distance,
      Math.sin(phi) * Math.sin(theta) * distance
    ],
    size: 0.08 + Math.random() * 0.25, // More size variation
    baseOpacity: baseOpacity,
    currentOpacity: baseOpacity,
    twinkleSpeed: 0.5 + Math.random() * 2, // Random twinkling speed
    twinklePhase: Math.random() * Math.PI * 2 // Random starting phase
  }
}))

// Table leg positions for fallback
const legPositions = [
  [-1.7, -2, -1.7],
  [1.7, -2, -1.7],
  [-1.7, -2, 1.7],
  [1.7, -2, 1.7]
]




// Start animation loop
onMounted(() => {
  let startTime = Date.now()
  let lastTime = 0

  const animate = () => {
    const currentTime = Date.now()
    const elapsed = (currentTime - startTime) / 1000
    const delta = (currentTime - lastTime) / 1000
    lastTime = currentTime

    // Crafting table stays static - no rotation

    // Update Xurkitree animations with proper delta time
    if (xurkitreeAnimationMixer) {
      xurkitreeAnimationMixer.update(delta) // Use delta time, not elapsed time
    }

    // Keep Xurkitree static rotation (let built-in animations handle movement)
    xurkitreeRotation.value = [0, 0, 0]

    // Animate star twinkling
    if (isDark.value) {
      stars.forEach(star => {
        // Create twinkling effect using sine wave
        const twinkle = Math.sin(elapsed * star.twinkleSpeed + star.twinklePhase)
        const twinkleIntensity = 0.3 // How much the star dims/brightens
        star.currentOpacity = star.baseOpacity + (twinkle * twinkleIntensity * star.baseOpacity)

        // Ensure opacity stays within valid range
        star.currentOpacity = Math.max(0.1, Math.min(1.0, star.currentOpacity))
      })
    }

    requestAnimationFrame(animate)
  }

  animate()
})
</script>

<style scoped>
.crafting-table-container {
  position: relative;
}

.pixelated-text {
  image-rendering: pixelated;
  image-rendering: -moz-crisp-edges;
  image-rendering: crisp-edges;
}

/* Three.js canvas styling */
.crafting-table-container canvas {
  display: block;
  border-radius: 0.5rem;
}
</style>